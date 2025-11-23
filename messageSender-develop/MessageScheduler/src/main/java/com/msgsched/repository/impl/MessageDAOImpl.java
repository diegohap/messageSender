package com.msgsched.repository.impl;

import com.msgsched.entity.Reminder;
import com.msgsched.repository.MessageDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageDAOImpl implements MessageDAO {
    private final Connection conn;

    public MessageDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void save(Reminder reminder) throws SQLException {
        String sql = "INSERT INTO message (message, send, email, whatsapp, state) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stm = null;

        try {
            stm = conn.prepareStatement(sql);
            stm.setString(1, reminder.getMessage());
            stm.setObject(2, reminder.getSend());
            stm.setBoolean(3, reminder.isEmail());
            stm.setBoolean(4, reminder.isWhatsapp());
            stm.setBoolean(5, reminder.isState());
            stm.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            stm.close();
        }
        finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Optional<Reminder> findById(int id) throws SQLException {
        String sql = "SELECT * FROM message WHERE id = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        Reminder result = null;

        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, id);

            rs = stm.executeQuery();
            if(rs.next()) {
                result = Reminder.builder()
                        .id(rs.getInt("id"))
                        .message(rs.getString("message"))
                        .send(rs.getObject("send", java.time.LocalDateTime.class))
                        .email(rs.getBoolean("email"))
                        .whatsapp(rs.getBoolean("whatsapp"))
                        .state(rs.getBoolean("state"))
                        .build();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public List<Reminder> findAll() throws SQLException {
        String sql = "SELECT * FROM message";
        PreparedStatement stm = null;
        List<Reminder> reminders = new ArrayList<>();

        try {
            stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Reminder reminder = Reminder.builder()
                        .id(rs.getInt("id"))
                        .message(rs.getString("message"))
                        .send(rs.getObject("send", java.time.LocalDateTime.class))
                        .email(rs.getBoolean("email"))
                        .whatsapp(rs.getBoolean("whatsapp"))
                        .state(rs.getBoolean("state"))
                        .build();
                reminders.add(reminder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return reminders;
    }

    @Override
    public void updateState(int id, boolean state) throws SQLException {
        String sql = "UPDATE message SET state = ? WHERE id = ?";
        PreparedStatement stm = null;

        try {
            stm = conn.prepareStatement(sql);
            stm.setBoolean(1, state);
            stm.setInt(2, id);
            stm.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Optional<Reminder> findNextPending() throws SQLException {
        String sql = "SELECT * FROM message WHERE state = 0 ORDER BY send ASC LIMIT 1";
        PreparedStatement stm = null;
        ResultSet rs = null;
        Reminder result = null;

        try {
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                result = Reminder.builder()
                        .id(rs.getInt("id"))
                        .message(rs.getString("message"))
                        .send(rs.getObject("send", java.time.LocalDateTime.class))
                        .email(rs.getBoolean("email"))
                        .whatsapp(rs.getBoolean("whatsapp"))
                        .state(rs.getBoolean("state"))
                        .build();
            }
        }
        finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException ignored) {}
            }
            if (stm != null) {
                try { stm.close(); } catch (SQLException ignored) {}
            }
        }
        return Optional.ofNullable(result);
    }
}
