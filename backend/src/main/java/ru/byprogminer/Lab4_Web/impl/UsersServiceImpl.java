package ru.byprogminer.Lab4_Web.impl;

import ru.byprogminer.Lab4_Web.users.UserEntity;
import ru.byprogminer.Lab4_Web.users.UsersService;

import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Stateless
public class UsersServiceImpl implements UsersService {

    private static final String PASSWORD_HASH_ALGORITHM = "SHA-1";
    private static final byte[] PASSWORD_PEPPER = "CUL/{)Rv9O1S".getBytes();

    private static final int PASSWORD_SALT_LENGTH = 12;
    private static final String PASSWORD_SALT_ALPHABET =
            "0123456789abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ`~!@#$%^&*()-+[]{}\\|,.<>/?";

    private final SecureRandom saltRandom = new SecureRandom();

    // TODO replace by repository
    private final Map<Long, UserEntity> users = new HashMap<>();
    private final AtomicLong nextId = new AtomicLong();

    @Override
    public UserEntity getUser(long userId) {
        return users.get(userId);
    }

    @Override
    public UserEntity findUser(@NotNull String username) {
        return users.values().stream()
                .filter(userEntity -> username.equals(userEntity.getUsername()))
                .findAny().orElse(null);
    }

    @Override
    public UserEntity createUser(@NotNull String username, @NotNull String password) {
        final String salt = generateSalt();

        final String hash = hashPassword(password, salt);

        return addUser(new UserEntity(null, username, hash, salt));
    }

    private UserEntity addUser(UserEntity user) {
        if (user.getId() != null) {
            user = new UserEntity(null, user.getUsername(), user.getPasswordHash(), user.getPasswordSalt());
        }

        if (findUser(user.getUsername()) != null) {
            return null;
        }

        final UserEntity persistedEntity = new UserEntity(
                nextId.incrementAndGet(),
                user.getUsername(),
                user.getPasswordHash(),
                user.getPasswordSalt()
        );

        users.put(persistedEntity.getId(), persistedEntity);
        return persistedEntity;
    }

    @Override
    public boolean removeUser(@NotNull UserEntity user) {
        return users.remove(user.getId()) != null;
    }

    @Override
    public boolean checkPassword(@NotNull UserEntity user, @NotNull String password) {
        return user.getPasswordHash().equals(hashPassword(password, user.getPasswordSalt()));
    }

    private String generateSalt() {
        final char[] saltChars = new char[PASSWORD_SALT_LENGTH];

        for (int i = 0; i < saltChars.length; ++i) {
            saltChars[i] = PASSWORD_SALT_ALPHABET.charAt(saltRandom.nextInt(PASSWORD_SALT_ALPHABET.length()));
        }

        return new String(saltChars);
    }

    private String hashPassword(String password, String salt) {
        final MessageDigest messageDigest;

        try {
            messageDigest = MessageDigest.getInstance(PASSWORD_HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no such password hash algorithm " + PASSWORD_HASH_ALGORITHM, e);
        }

        messageDigest.update(PASSWORD_PEPPER);
        messageDigest.update(password.getBytes());
        messageDigest.update(salt.getBytes());
        final byte[] digest = messageDigest.digest();

        final StringBuilder hash = new StringBuilder();
        for (byte b : digest) {
            hash.append(String.format("%02x", b));
        }

        return hash.toString();
    }
}
