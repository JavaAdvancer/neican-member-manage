package com.jiyou.nm.common.base;

/**
 * 模型属性类型常量
 */
public interface ModelAttrType {

    /**
     * 字典
     */
    String DICT = "dict";

    /**
     * 文本
     */
    String INPUT = "input";
    /**
     * 单选下拉菜单
     */
    String SELECT = "select";
    /**
     * 多选下拉菜单
     */
    String MULTIPLESELECT = "multipleSelect";
    /**
     * 时间 yyyy-MM-dd
     */
    String DATEPICKER = "datePicker";

    /**
     * 时间 yyyy-MM-dd HH:mm:ss
     */
    String DATETIMEPICKER = "dateTimePicker";

    /**
     * 地址
     */
    String ADDRESSPICKER = "addressPicker";
    /**
     * 数值
     */
    String NUMBER = "number";
    /**
     * 模型
     */
    String MODEL = "model";
    /**
     * 订单类型id
     */
    Integer ORDERTYPREID = 50;
    /**
     * 受众类型id
     */
    Integer AUDIENCES_TYPE_ID = 49;


    public static interface Suffix{

        String ADDRESSPICKER = "code";
    }

    public static interface DateFormat{

        String CSTM_LOCAl_DATE ="yyyy-MM-dd";
        String CSTM_LOCAl_DATE_TIME ="yyyy-MM-dd HH:mm:ss";
    }

    public static interface FixedAttr{
        String SOURCE_SYSTEM_ID ="source_system_id";
        String INNER_SOURCE_SYSTEM_VALUE ="inner";
        String SOURCE_SYSTEM ="source_system";
        String CUSTOMER_ID ="customer_id";
        String USER_MODEL_INDEX_PREFIX = "customer_";
        String PRODUCT_MODEL_INDEX_PREFIX = "product_";
        String MODEL_ID  =  "modelId";
        String USER_ID  =  "userId";
        String DATA_TYPE  =  "data_type";
        String MODEL_TYPE  =  "model_type";
        String SYS_CREATE_TIME  =  "sys_create_time";
        String CREATE_TIME  =  "create_time";
        String JOIN_DATE  =  "join_date";
        String JOIN_DATE_VALUE  =  "join_date.value";
        String JOIN_DATE_DAYOFWEEK  =  "join_date.dayOfWeek";
        String JOIN_DATE_DAY = "join_date.day";
        String JOIN_DATE_LASTDAY = "join_date.isLastDayOfMonth";
        String SYS_ID  =  "_id";
        String ES_INDEX_DOC  =  "_doc";
        String ID  =  "id";
        String ES_ID = "esId";
        String STAGE_ID ="stage_id";
        String IS_DELETE = "is_delete";
        String SCORE = "score";
        String VALUE_SCORING = "value_scoring";
        String ACTIVITY = "activity";
        String SEVEN_ACTIVITY = "seven_activity";
        String THIRTY_ACTIVITY = "thirty_activity";
        String CUSTOMER_JOIN = "customer_join";
        String CUSTOMER_JOIN_CUSTOMER = "customer";
        String GROUPS = "groups";
        String RELATION_ATTR_ID = "relationAttrId";
        String RELATION_ATTR_IDENTITY = "relationAttrIdentity";
        String RELATION_DATA_LIST = "relationDataList";
        String IDENTITYS = "identitys";
        String INIT_SOURCE = "source_id";
        String LASTUPDATE_TIME = "lastupdate_time";
        String TAGS = "tags";
        String STATIC_GROUP_LIST = "staticGroupList";
        String DYNAMIC_GROUP_LIST = "dynamicGroupList";
        String EVENT_IDENTITY = "event_identity";
        String MODEL_DATA_ID = "model_data_id";
        String MODEL_DATA_ID_KEY = "model_data_id.keyword";
        /**
         * 事件的固定属性
         */
        String EVENT_TAGS = "event_tags";

        /*订单商品固定属性*/
        String GOODS_LIST="goods_list";
        String DEALCODE="dealcode";
        String ITEM_CODE="item_code";
        String ITEM_NAME="item_name";
        String ITEM_SIMPLE_NAME="item_simple_name";
        String SKU_NAME="sku_name";
        String SKU_CODE="sku_code";
        String QTY="qty";
        String PRICE="price";
        String DISCOUNT_FEE="discount_fee";
        String AMOUNT_AFTER="amount_after";
        String NOTE="note";

        /*收获地址属性*/
        String PERSONAL_RECEIVER_ADDRESS="personal_receiver_address";
        String CONSIGNEE_NAME="consignee_name";
        String CONSIGNEE_NUMBER="consignee_number";
        String CONSIGNEE_PROVINCE="consignee_province";
        String CONSIGNEE_CITY="consignee_city";
        String CONSIGNEE_COUNTY="consignee_county";
        String CONSIGNEE_ADDRESS="consignee_address";
        String IS_DEFAULT="isDefault";
    }


    public static interface ModelType{
        String MODEL = "model";
        String EVENT = "event";
        String ORDER = "order";
        String PRODUCT = "product";
        String FOLLOW_PUBLIC = "follow_public";
    }

    public static interface FOLLOWPUBLICTYPE{
        String FOLLOWNEVER = "follow_never";
        String FOLLOWNOW = "follow_now";
        String FOLLOWCANCEL = "follow_cancel";
    }

    public static interface JoinType{
        String JOIN = "join";
        String EVENT = "c_event";
        String ORDER = "c_order";
    }

    public static interface MemberAttr{
        Integer source = 6;
        Integer stage = 1;

    }

    public static interface NewModelAttr{
        Integer USERID=14;
        Integer MODELID=232;
        String CUSTOMER = "customer_149";
        String COUNTRY = "country_26";
        String CUSTOMER_TYPE = "customertype_29";
        String QUDAO = "qudao_27";
        String TIME = "shouhuo_28";
        String SYS_CREATE_TIME = "sys_create_time";
        String OLD_CREATE_TIME = "old_create_time_31";
        String LASTUPDATE_TIME = "lastupdate_time";
        String AREA = "area";
        String SOURCE_SYSTEM ="source_system";
        String SOURCE_SYSTEM_ID ="source_system_id";
        String INDEX_NAME="dm_customer";
        String HUIYUAN="huiyuan";

    }

   /* public static interface NewModelAttr{
        Integer USERID=30;
        Integer MODELID=240;
        String CUSTOMER = "customer_149";
        String COUNTRY = "country";
        String CUSTOMER_TYPE = "customertype_29";
        String QUDAO = "qudao_27";
        String TIME = "shouhuo_28";
        String SYS_CREATE_TIME = "sys_create_time";
        String OLD_CREATE_TIME = "old_create_time_31";
        String LASTUPDATE_TIME = "lastupdate_time";
        String AREA = "area";
        String SOURCE_SYSTEM ="source_system";
        String SOURCE_SYSTEM_ID ="source_system_id";
        String INDEX_NAME="dm_customer";
        String HUIYUAN="huiyuan";

    }*/

   /* public static interface NewModelAttr{
        Integer USERID=1;
        Integer MODELID=328;
        String CUSTOMER = "customer_149";
        String COUNTRY = "country_26";
        String CUSTOMER_TYPE = "customertype_29";
        String QUDAO = "qudao_27";
        String TIME = "shouhuo_28";
        String SYS_CREATE_TIME = "sys_create_time";
        String OLD_CREATE_TIME = "old_create_time_155";
        String LASTUPDATE_TIME = "lastupdate_time";
        String AREA = "area";
        String SOURCE_SYSTEM ="source_system";
        String SOURCE_SYSTEM_ID ="source_system_id";
        String INDEX_NAME="dm_customer";
        String HUIYUAN="huiyuan";

    }*/

}
