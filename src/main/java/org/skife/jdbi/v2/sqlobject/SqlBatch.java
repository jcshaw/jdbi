package org.skife.jdbi.v2.sqlobject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate a method to indicate that it will create and execute a SQL batch. At least one
 * bound argument must be an Iterator or Iterable, values from this will be taken and applied
 * to each row of the batch. Non iterable bound arguments will be treated as constant values and
 * bound to each row.
 * <p>
 * Unfortunately, because of how batches work, statement customizers and sql statement customizers
 * which affect SQL generation will *not* work with batches. This primarily effects statement location
 * and rewriting, which will always use the values defined on the bound Handle.
 * <p>
 * If you want to chunk up the logical batch into a number of smaller batches (say around 1000 rows at
 * a time in order to not wreck havoc on the transaction log, you should see
 * {@link org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SqlBatch
{
    /**
     * SQL String (or name)
     */
    String value() default SqlQuery.DEFAULT_VALUE;

    /**
     * Should the batch, or batch chunks be executed in a transaction. Default is
     * true (and it will be strange if you want otherwise).
     */
    boolean transactional() default true;
}