package dal.repository;

public class EntityNotFoundException extends Exception{
    private int entityId;
    private String entityClass;

    public EntityNotFoundException(int entityId, String entityClass) {
        this.entityId = entityId;
        this.entityClass = entityClass;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }

}
