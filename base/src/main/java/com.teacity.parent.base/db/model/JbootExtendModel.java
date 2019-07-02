package com.teacity.parent.base.db.model;

import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;
import com.jfinal.plugin.ehcache.IDataLoader;
import io.jboot.Jboot;
import io.jboot.db.model.JbootModel;

import java.util.List;


public class JbootExtendModel<M> extends JbootModel {
    public String tableName() {
        return TableMapping.me().getTable(this.getUsefulClass()).getName();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Class<? extends JbootModel> getUsefulClass() {
        Class c = getClass();
        return c.getName().indexOf("EnhancerByCGLIB") == -1 ? c : c.getSuperclass();
    }

    private static final String COLUMN_CREATED = "created";

    public void removeCache(Object key) {
        if (key == null) return;
        Jboot.me().getCache().remove(tableName(), key);

    }

    public void putCache(Object key, Object value) {
        Jboot.me().getCache().put(tableName(), key, value);
    }

    public M getCache(Object key) {
        return Jboot.me().getCache().get(tableName(), key);

    }

    public JbootModel getCache(Object key, IDataLoader dataloader) {
        return Jboot.me().getCache().get(tableName(), key, dataloader);

    }

    public List<M> getListCache(Object key, IDataLoader dataloader) {
        return Jboot.me().getCache().get(tableName(), key, dataloader);

    }

    public String buildCacheKey(String column, String value) {
        return String.format("%s:%s", column, value);
    }

    public boolean saveOrUpdate() {
        if (null == get(getPrimaryKey())) {
            return this.save();
        }
        return this.update();
    }


    @Override
    public boolean save() {

        boolean saved = super.save();
        if (saved) {
            Jboot.sendEvent(addAction(), this);

        }
        return saved;
    }


    @Override
    public boolean delete() {
        boolean deleted = super.delete();
        if (deleted) {
            removeCache(get(getPrimaryKey()));
            Jboot.sendEvent(deleteAction(), this);

        }
        return deleted;
    }


    @Override
    public boolean deleteById(Object idValue) {
        JbootModel<?> model = findById(idValue);
        return model.delete();
    }


    @Override
    public boolean update() {

        boolean update = super.update();
        if (update) {
            removeCache(get(getPrimaryKey()));
            Jboot.sendEvent(updateAction(), findById(get(getPrimaryKey())));

        }
        return update;
    }

    protected String addAction() {
        return tableName() + ":add";
    }

    protected String deleteAction() {
        return tableName() + ":delete";
    }

    protected String updateAction() {
        return tableName() + ":update";
    }

    @Override
    public JbootModel findById(final Object idValue) {
        return getCache(idValue, new IDataLoader() {
            @Override
            public JbootModel load() {
                return findByIdWithoutCache(idValue);
            }
        });
    }


    public JbootModel findByIdWithoutCache(Object idValue) {
        return super.findById(idValue);
    }


    public String cacheName() {
        return tableName();
    }


    private void appendDefaultOrderBy(StringBuilder sqlBuilder) {
        if (hasColumn(COLUMN_CREATED)) {
            sqlBuilder.append(" order by created desc");
        }
    }


    protected String getPrimaryKey() {
        String[] primaryKeys = getPrimaryKeys();
        if (null != primaryKeys && primaryKeys.length == 1) {
            return primaryKeys[0];
        }
        throw new RuntimeException(String.format("get PrimaryKey is error in[%s]", getClass()));
    }

    protected String[] getPrimaryKeys() {
        Table t = TableMapping.me().getTable(getUsefulClass());
        if (t == null) {
            throw new RuntimeException("can't get table of " + getUsefulClass() + " , maybe jpress install incorrect");
        }
        return t.getPrimaryKey();
    }


    protected boolean hasColumn(String columnLabel) {
        return TableMapping.me().getTable(getUsefulClass()).hasColumnLabel(columnLabel);
    }


}
