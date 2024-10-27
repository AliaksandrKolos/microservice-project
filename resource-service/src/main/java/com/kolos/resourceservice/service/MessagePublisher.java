package com.kolos.resourceservice.service;

import java.util.List;

public interface MessagePublisher {

    void postId(Long id);

    void postIds(List<Long> ids);

    void healthCheck();

}
