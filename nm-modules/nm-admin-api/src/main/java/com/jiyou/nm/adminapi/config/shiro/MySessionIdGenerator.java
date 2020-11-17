package com.jiyou.nm.adminapi.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

public class MySessionIdGenerator implements SessionIdGenerator {

    private static final Logger log = LoggerFactory.getLogger(MySessionIdGenerator.class);
    private static final String RANDOM_NUM_GENERATOR_ALGORITHM_NAME = "SHA1PRNG";

    @Override
    public Serializable generateId(Session session) {
        String uuid =  UUID.randomUUID().toString().replaceAll("-","");
        Random  random;
        try {
            random = java.security.SecureRandom.getInstance(RANDOM_NUM_GENERATOR_ALGORITHM_NAME);
        } catch (java.security.NoSuchAlgorithmException e) {
            log.debug("The SecureRandom SHA1PRNG algorithm is not available on the current platform.  Using the " +
                    "platform's default SecureRandom algorithm.", e);
            random = new java.security.SecureRandom();
        }
        return uuid+random.nextLong();

    }

}
