package general.concurrency.taskframework.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;
import org.mongojack.DBQuery;
import org.mongojack.DBUpdate;
import org.mongojack.Id;
import org.mongojack.WriteResult;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author asia created on 10/10/15.
 */
@Setter
@Getter
@AllArgsConstructor
public class SavedOffset<K> implements Model<String> {

    // task name
    @Id private String id;
    @JsonProperty private int offset;
    @JsonProperty private List<K> failedRecords;
    /**
     * This is the key value (used only when key is set) of the last processed record.
     */
    @JsonProperty @JsonSerialize private K lastRecord;
    @JsonProperty private Key key;

    public SavedOffset(String taskName) {
        this.id = taskName;
        offset = 0;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String key) {
        this.id = key;
    }

    public static void main(String[] args) {

    }

    public static class Dao<K> extends BaseDao<SavedOffset<K>, String> {
        public static final String COLLECTION_NAME = "savedOffset";

        public Dao() {
            super(COLLECTION_NAME, getBaseGenericParamClass(), String.class);
        }

        public int saveFailed(String id, K failedRecord) {
            WriteResult<SavedOffset<K>, String> res = coll.updateById(id, DBUpdate.addToSet("failedRecords", failedRecord));
            return res.getN();
        }

        public void updateOffset(SavedOffset<K> toUpdate, int offset) {
            WriteResult<SavedOffset<K>, String> result = coll.update(
                    DBQuery.is("_id", toUpdate.id).notExists("key"),
                    DBUpdate.set("offset", offset));
            if (result.getN() != 1) {
                throw new RuntimeException("Failed to update offset for " + toUpdate.id);
            }
        }

        public void updateOffset(SavedOffset<K> toUpdate, int offset, K lastRecord) {
            WriteResult<SavedOffset<K>, String> result = coll.update(
                    DBQuery.is("_id", toUpdate.id).is("key", Validate.notNull(toUpdate.key)),
                    DBUpdate.set("offset", offset).set("lastRecord", lastRecord));
            if (result.getN() != 1) {
                throw new RuntimeException("Failed to update offset for " + toUpdate.id);
            }
        }

        private static <K> Class<SavedOffset<K>> getBaseGenericParamClass() {
            ParameterizedType superType = (ParameterizedType) Dao.class.getGenericSuperclass();
            Type[] paramTypes = superType.getActualTypeArguments();
            ParameterizedType firstType = (ParameterizedType) paramTypes[0];
            return (Class<SavedOffset<K>>) firstType.getRawType();
        }
    }

    public static class Key {
        /**
         * This is the name of the field against which to compare the lastRecord.
         * Normally this would be "_id".
         */
        @JsonProperty
        public final String fieldName;

        /**
         * Traversal order: 1 for ascending, -1 for descending
         */
        @JsonProperty
        public final int order;

        @JsonCreator
        public Key(@JsonProperty("fieldName") String fieldName, @JsonProperty("order") int order) {
            Validate.notBlank(fieldName);
            Validate.isTrue(order == 1 || order == -1, "Order must be either 1 or -1", order);
            this.fieldName = fieldName;
            this.order = order;
        }
    }

    public enum Mode {
        None,
        // Continue previous offset in Database if exists otherwise start from new
        Continue,
        // Always start a new offset, overwrite previous offset if it exists in DB
        Overwrite
    }
}
