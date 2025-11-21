package com.msgsched.repository;

import com.msgsched.entity.Reminder;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MessageDAO {
    void save(Reminder reminder) throws SQLException;
    Optional<Reminder> findById(int id) throws SQLException;
    List<Reminder> findAll() throws SQLException;
    void updateState(int id, boolean state) throws SQLException;
    Optional<Reminder> findNextPending() throws SQLException;
}
