package com.test.Web.repos;

import com.test.Web.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByTag(String tag);
}
