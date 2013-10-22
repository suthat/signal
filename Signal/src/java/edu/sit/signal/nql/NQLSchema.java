package edu.sit.signal.nql;

/**
 *
 * @author Suthat
 */
public class NQLSchema {

    private NQLSchema.Signalnql signalnql;

    /**
     * @return the signalnql
     */
    public NQLSchema.Signalnql getSignalnql() {
        return signalnql;
    }

    /**
     * @param signalnql the signalnql to set
     */
    public void setSignalnql(NQLSchema.Signalnql signalnql) {
        this.signalnql = signalnql;
    }

    static class Signalnql {

        private NQLSchema.Select select;
        private NQLSchema.From from;
        private NQLSchema.Where where;
        private NQLSchema.On on;
        private NQLSchema.Device device;
        private NQLSchema.Order order;
        private NQLSchema.Limit limit;

        /**
         * @return the select
         */
        public NQLSchema.Select getSelect() {
            return select;
        }

        /**
         * @param select the select to set
         */
        public void setSelect(NQLSchema.Select select) {
            this.select = select;
        }

        /**
         * @return the from
         */
        public NQLSchema.From getFrom() {
            return from;
        }

        /**
         * @param from the from to set
         */
        public void setFrom(NQLSchema.From from) {
            this.from = from;
        }

        /**
         * @return the where
         */
        public NQLSchema.Where getWhere() {
            return where;
        }

        /**
         * @param where the where to set
         */
        public void setWhere(NQLSchema.Where where) {
            this.where = where;
        }

        /**
         * @return the on
         */
        public NQLSchema.On getOn() {
            return on;
        }

        /**
         * @param on the on to set
         */
        public void setOn(NQLSchema.On on) {
            this.on = on;
        }

        /**
         * @return the device
         */
        public NQLSchema.Device getDevice() {
            return device;
        }

        /**
         * @param device the device to set
         */
        public void setDevice(NQLSchema.Device device) {
            this.device = device;
        }

        /**
         * @return the order
         */
        public NQLSchema.Order getOrder() {
            return order;
        }

        /**
         * @param order the order to set
         */
        public void setOrder(NQLSchema.Order order) {
            this.order = order;
        }

        /**
         * @return the limit
         */
        public NQLSchema.Limit getLimit() {
            return limit;
        }

        /**
         * @param limit the limit to set
         */
        public void setLimit(NQLSchema.Limit limit) {
            this.limit = limit;
        }
    }

    static class Select {

        private String entities[];

        /**
         * @return the entities
         */
        public String[] getEntities() {
            return entities;
        }

        /**
         * @param entities the entities to set
         */
        public void setEntities(String[] entities) {
            this.entities = entities;
        }
    }

    static class From {

        private String dataSet;

        /**
         * @return the dataSet
         */
        public String getDataSet() {
            return dataSet;
        }

        /**
         * @param dataSet the dataSet to set
         */
        public void setDataSet(String dataSet) {
            this.dataSet = dataSet;
        }
    }

    static class Where {

        private String conditionKey;
        private String conditionValue;

        /**
         * @return the conditionKey
         */
        public String getConditionKey() {
            return conditionKey;
        }

        /**
         * @param conditionKey the conditionKey to set
         */
        public void setConditionKey(String conditionKey) {
            this.conditionKey = conditionKey;
        }

        /**
         * @return the conditionValue
         */
        public String getConditionValue() {
            return conditionValue;
        }

        /**
         * @param conditionValue the conditionValue to set
         */
        public void setConditionValue(String conditionValue) {
            this.conditionValue = conditionValue;
        }
    }

    static class On {

        private String datetimes;

        /**
         * @return the datetimes
         */
        public String getDatetimes() {
            return datetimes;
        }

        /**
         * @param datetimes the datetimes to set
         */
        public void setDatetimes(String datetimes) {
            this.datetimes = datetimes;
        }
    }
    
    static class Device {
        
        private String devices;

        /**
         * @return the devices
         */
        public String getDevices() {
            return devices;
        }

        /**
         * @param devices the devices to set
         */
        public void setDevices(String devices) {
            this.devices = devices;
        }
    }

    static class Order {

        private String sortEntity;
        private String sortDomain;

        /**
         * @return the sortEntity
         */
        public String getSortEntity() {
            return sortEntity;
        }

        /**
         * @param sortEntity the sortEntity to set
         */
        public void setSortEntity(String sortEntity) {
            this.sortEntity = sortEntity;
        }

        /**
         * @return the sortDomain
         */
        public String getSortDomain() {
            return sortDomain;
        }

        /**
         * @param sortDomain the sortDomain to set
         */
        public void setSortDomain(String sortDomain) {
            this.sortDomain = sortDomain;
        }
    }

    static class Limit {

        private String queryRange;

        /**
         * @return the queryRange
         */
        public String getQueryRange() {
            return queryRange;
        }

        /**
         * @param queryRange the queryRange to set
         */
        public void setQueryRange(String queryRange) {
            this.queryRange = queryRange;
        }
    }
}
