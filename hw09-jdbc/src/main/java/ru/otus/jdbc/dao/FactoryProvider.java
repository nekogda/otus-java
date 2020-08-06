package ru.otus.jdbc.dao;

public class FactoryProvider {
    private FactoryProvider() {
    }

    public static DaoAbstractFactory<?> getFactory(String modelName) {
        switch (modelName) {
            case "User":
                return new UserDaoFactory();
            default:
                throw new IllegalArgumentException("expected model name");
        }
    }
}
