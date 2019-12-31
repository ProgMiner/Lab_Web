package ru.byprogminer.Lab4_Web.users;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Stateless
public class UsersServiceBean implements UsersService {

    private static final String PASSWORD_HASH_ALGORITHM = "SHA-1";
    private static final byte[] PASSWORD_PEPPER = "CUL/{)Rv9O1S".getBytes();

    private static final int PASSWORD_SALT_LENGTH = 12;
    private static final String PASSWORD_SALT_ALPHABET =
            "0123456789abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ`~!@#$%^&*()-+[]{}\\|,.<>/?";

    private final SecureRandom saltRandom = new SecureRandom();

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserEntity getUser(long userId) {
        return entityManager.find(UserEntity.class, userId);
    }

    @Override
    public UserEntity findUser(@NotNull String username) {
        return entityManager.createNamedQuery("users.findByUsername", UserEntity.class)
                .setParameter("username", username).getResultStream()
                .findAny().orElse(null);
    }

    @Override
    public UserEntity createUser(@NotNull @Size(min = 2) @NotBlank String username, @NotNull @NotBlank String password) {
        final String salt = generateSalt();

        final String hash = hashPassword(password, salt);

        try {
            final UserEntity entity = new UserEntity(null, username, hash, salt);
            entityManager.persist(entity);
            entityManager.flush();

            return entity;
        } catch (PersistenceException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean removeUser(@NotNull UserEntity user) {
        try {
            entityManager.remove(entityManager.getReference(UserEntity.class, user.getId()));
            entityManager.flush();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }

        return true;
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
