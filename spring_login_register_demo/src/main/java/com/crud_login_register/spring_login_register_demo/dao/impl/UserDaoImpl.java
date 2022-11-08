package com.crud_login_register.spring_login_register_demo.dao.impl;

import com.crud_login_register.spring_login_register_demo.constants.Constants;
import com.crud_login_register.spring_login_register_demo.controller.bean.ShowUser;
import com.crud_login_register.spring_login_register_demo.controller.bean.User;
import com.crud_login_register.spring_login_register_demo.controller.bean.UserRegister;
import com.crud_login_register.spring_login_register_demo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl extends JdbcDaoSupport implements UserDao {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

    @Override
    public User getUserByName(String userName) {

        String sql2="select id from user where user_name=?";
        List<Integer> returnvalue = getJdbcTemplate().query(sql2,new Object[] {userName} ,new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt(1);
            }
        });
        if(returnvalue.size() != 1) return null;

        String sql="select user_name,cast(aes_decrypt(unhex(`user_password`), 'secret') as char(50)) as password_unencrypted,gender_id ,board_id,joining_date, photo, role_id\n" +
                " from user join user_role on user.id = user_role.user_id \n" +
                " where user.user_name=? group by user.id";

        User user =getJdbcTemplate().queryForObject(sql, new Object[] {userName}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user1 =new User();
                user1.setUserName(userName);
                user1.setPassword(rs.getString(2));
                user1.setGender_id(rs.getInt(3));
                user1.setBoard_id(rs.getInt(4));
                user1.setJoining_date(rs.getString(5));
                user1.setRoleId(rs.getInt(7));

                if(rs.getBlob(6) != null)
                {
                    Blob blob = rs.getBlob(6);
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;

                    while (true) {
                        try {
                            if (!((bytesRead = inputStream.read(buffer)) != -1)) break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    byte[] imageBytes = outputStream.toByteArray();
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);


                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    user1.setBase64file(base64Image);
                }

                return user1;
            }
        });

        return user;
    }

    @Override
    public int createNewUser(User user , InputStream inputStream) {

        String sql1="insert into user(user_name,user_password,gender_id,board_id,joining_date,photo) values(?, hex(aes_encrypt(?,'secret')),?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(sql1 , new String[] { "id" });
                preparedStatement.setString(1,user.getUserName());
                preparedStatement.setString(2,user.getPassword());
                preparedStatement.setInt(3,user.getGender_id());
                preparedStatement.setInt(4,user.getBoard_id());
                preparedStatement.setString(5,user.getJoining_date());
                if (inputStream != null) {
                    preparedStatement.setBlob(6, inputStream);
                }
                return preparedStatement;
            }
        },keyHolder);

        int id=keyHolder.getKey().intValue();

        String sql2 = "insert into user_role(role_id, user_id) values(?, ?)";
        int returnvalue1 = getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql2);
                ps.setInt(1, Constants.ROLE_EMPLOYEE);
                ps.setInt(2, id);
                return ps;
            }
        });

        int returnvalue2 =0 ;
        for(int certificate_id : user.getCertificate_ids())
        {
            String sql3 = "insert into user_certificates(certificate_id , user_id) values(?, ?)";
            returnvalue2 = getJdbcTemplate().update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement ps = con.prepareStatement(sql3);
                    ps.setInt(1, certificate_id);
                    ps.setInt(2, id);
                    return ps;
                }
            });
        }
        System.out.println("Created user_______________________________________");
        System.out.println(user.toString());
        return (returnvalue1*returnvalue2);
    }

    @Override
    public int updateUser(User user) {
//        System.out.println("upadte user dao called for ");
//        System.out.println(user.getUserName());
        String sql="update user set `user_password`= hex(aes_encrypt(?,'secret') ) , gender_id=? , board_id=? , joining_date=?  " +
                "where user_name=?;";

        int returnvalue1 = getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1,user.getPassword());
                ps.setInt(2,user.getGender_id());
                ps.setInt(3,user.getBoard_id());
                ps.setString(4,user.getJoining_date());
                ps.setString(5,user.getUserName());
                return ps;
            }
        });

        String sql2="delete from user_certificates where user_id=(select id from user where user_name=?);";
        int returnvalue3 = getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql2);
                ps.setString(1, user.getUserName());
                return ps;
            }
        });

        int returnvalue2 =0 ;
        for(int certificate_id : user.getCertificate_ids())
        {
            String sql3 = "insert into user_certificates(certificate_id , user_id) values(?, (select id from user where user_name=?))";
            returnvalue2 = getJdbcTemplate().update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement ps = con.prepareStatement(sql3);
                    ps.setInt(1, certificate_id);
                    ps.setString(2, user.getUserName());
                    return ps;
                }
            });
        }

        System.out.println("update function return value");
        System.out.println(returnvalue3);
        return returnvalue1*returnvalue2;
    }

    @Override
    public int deleteUser(User user) {
        String sql1 = "delete from user_certificates where user_id=(select id from user where user_name=?);";
        String sql2 = "delete from user_role where user_id=(select id from user where user_name=?);";
        String sql3 ="delete from user where user_name=?;";
        int returnvalue = getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql1);
                ps.setString(1, user.getUserName());
                return ps;
            }
        });
        returnvalue = getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql2);
                ps.setString(1, user.getUserName());
                return ps;
            }
        });
        returnvalue = getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql3);
                ps.setString(1, user.getUserName());
                return ps;
            }
        });

        System.out.println("Delete return Value");
        System.out.println(returnvalue);
        return returnvalue;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "select * from user";

        return getJdbcTemplate().query(sql, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setUserName(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setGender_id(rs.getInt(4));
                user.setBoard_id(rs.getInt(5));
                user.setJoining_date(rs.getString(6));
                return user;
            }

        });
    }

    @Override
    public ShowUser showUserDetails(String userName) {
        String sql = "select user_name,cast(aes_decrypt(unhex(`user_password`), 'secret') as char(50)) as password_unencrypted, gender_name,board_name , joining_date,photo,GROUP_CONCAT(certificate_name ORDER BY certificate_id ASC SEPARATOR ', ') certificate_names\n" +
                "from user u inner join l_gender g on u.gender_id = g.id inner join l_board b on u.board_id = b.id inner join user_certificates uc on uc.user_id = u.id inner join l_certificate c on uc.certificate_id=c.id \n" +
                "where u.user_name=?;";

        return getJdbcTemplate().queryForObject(sql,new Object[] {userName},new RowMapper<ShowUser>() {

            @Override
            public ShowUser mapRow(ResultSet rs, int rowNum) throws SQLException {
                ShowUser showUser = new ShowUser();
                showUser.setUserName(rs.getString(1));
                showUser.setPassword(rs.getString(2));
                showUser.setGender(rs.getString(3));
                showUser.setBoard(rs.getString(4));
                showUser.setJoining_date(rs.getString(5));
                showUser.setCertificates(rs.getString(7));

                Blob blob = rs.getBlob(6);
                InputStream inputStream = blob.getBinaryStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;

                while (true) {
                    try {
                        if (!((bytesRead = inputStream.read(buffer)) != -1)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    outputStream.write(buffer, 0, bytesRead);
                }

                byte[] imageBytes = outputStream.toByteArray();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);


                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                showUser.setBase64file(base64Image);
                return showUser;
            }

        });
    }



    @Override
    public List<Integer> getCertificateIds(User user)
    {
        String sql = "select certificate_id from user_certificates where user_id=(select id from user where user_name=?)";

        List<Integer> returnvalue = getJdbcTemplate().query(sql,new Object[] {user.getUserName()} ,new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt(1);
            }

        });
        return returnvalue;
    }

    @Override
    public int cerateNewRegisteredUser(UserRegister userRegister) {
        int ret=0;
        String sql = "INSERT user_register (user_name, user_password, father_name, mother_name, " +
                "address, nid, email, contact_no, dob) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        ret = getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(sql , new String[] { "id" });
                preparedStatement.setString(1,userRegister.getUserName());
                preparedStatement.setString(2,userRegister.getPassword());
                preparedStatement.setString(3,userRegister.getFatherName());
                preparedStatement.setString(4,userRegister.getMotherName());
                preparedStatement.setString(5,userRegister.getAddress());
                preparedStatement.setString(6,userRegister.getNid());
                preparedStatement.setString(7,userRegister.getEmail());
                preparedStatement.setString(8,userRegister.getContactNo());
                preparedStatement.setString(9,userRegister.getDateOfBirth());
                return preparedStatement;
            }
        });
        return ret;
    }

    @Override
    public List<UserRegister> getAllRegisteredUser() {
        String sql = "select * from user_register";

        return getJdbcTemplate().query(sql, new RowMapper<UserRegister>() {

            @Override
            public UserRegister mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserRegister userRegister = new UserRegister();
                userRegister.setId(rs.getInt(1));
                userRegister.setUserName(rs.getString(2));
                userRegister.setPassword(rs.getString(3));
                userRegister.setFatherName(rs.getString(4));
                userRegister.setMotherName(rs.getString(5));
                userRegister.setAddress(rs.getString(6));
                userRegister.setNid(rs.getString(7));
                userRegister.setEmail(rs.getString(8));
                userRegister.setContactNo(rs.getString(9));
                userRegister.setDateOfBirth(rs.getString(10));
                return userRegister;
            }

        });

    }

    @Override
    public UserRegister getById(int id) {
        String sql = "select * from user_register where id = ?";

        List<UserRegister> userRegisters;
        userRegisters = getJdbcTemplate().query(sql,new Object[] {id} , new RowMapper<UserRegister>() {

            @Override
            public UserRegister mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserRegister userRegister = new UserRegister();
                userRegister.setId(rs.getInt(1));
                userRegister.setUserName(rs.getString(2));
                userRegister.setPassword(rs.getString(3));
                userRegister.setFatherName(rs.getString(4));
                userRegister.setMotherName(rs.getString(5));
                userRegister.setAddress(rs.getString(6));
                userRegister.setNid(rs.getString(7));
                userRegister.setEmail(rs.getString(8));
                userRegister.setContactNo(rs.getString(9));
                userRegister.setDateOfBirth(rs.getString(10));
                return userRegister;
            }

        });
        if(userRegisters.size() == 1) return userRegisters.get(0);
        return null;
    }

    @Override
    public int updateRegisteredUser(UserRegister userRegister) {
        //UserRegister userRegister = getById(id);
        String sql = "UPDATE user_register SET user_name = ? , user_password = ?, father_name = ?, mother_name = ?, address = ?, " +
                "nid = ?, email = ?, contact_no = ?, dob = ? WHERE id = ?;";
        return getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1,userRegister.getUserName());
                ps.setString(2,userRegister.getPassword());
                ps.setString(3,userRegister.getFatherName());
                ps.setString(4,userRegister.getMotherName());
                ps.setString(5,userRegister.getAddress());
                ps.setString(6,userRegister.getNid());
                ps.setString(7,userRegister.getEmail());
                ps.setString(8,userRegister.getContactNo());
                ps.setString(9,userRegister.getDateOfBirth());
                ps.setInt(10,userRegister.getId());
                return ps;
            }
        });
    }

    @Override
    public int deleteRegistereduser(int id) {
        String sql = "DELETE FROM user_register WHERE (id = ?);";

        return getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                return ps;
            }
        });
    }

    @Override
    public UserRegister getRegisteredUserByUserName(String userName) {
        String sql1="select id from user_register where user_name=?";
        List<Integer> returnvalue = getJdbcTemplate().query(sql1,new Object[] {userName} ,new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt(1);
            }
        });
        if(returnvalue.size() != 1) return null;

        String sql2="select password" +
                " from user_register where user_name=?";

        UserRegister userRegister =getJdbcTemplate().queryForObject(sql2, new Object[] {userName}, new RowMapper<UserRegister>() {
            @Override
            public UserRegister mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserRegister userRegister1 =new UserRegister();
                userRegister1.setUserName(userName);
                userRegister1.setPassword(rs.getString(1));
                return userRegister1;
            }
        });

        return userRegister;
    }

}
