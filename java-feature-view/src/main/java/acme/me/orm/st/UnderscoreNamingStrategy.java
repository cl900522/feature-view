package acme.me.orm.st;

import org.hibernate.cfg.NamingStrategy;

public class UnderscoreNamingStrategy implements NamingStrategy {

    @Override
    public String classToTableName(String className) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String propertyToColumnName(String propertyName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String tableName(String tableName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String columnName(String columnName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable, String propertyName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String joinKeyColumnName(String joinedColumn, String joinedTable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String logicalColumnName(String columnName, String propertyName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String logicalCollectionTableName(String tableName, String ownerEntityTable, String associatedEntityTable, String propertyName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String logicalCollectionColumnName(String columnName, String propertyName, String referencedColumn) {
        // TODO Auto-generated method stub
        return null;
    }

}
