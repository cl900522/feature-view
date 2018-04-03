package acme.me.cache.util;

/**
 * Created by caijun.yang on 2018/3/21
 */
public interface RedisClient {


    /**
     * 字符串类型:通过key值获取对应的value对象
     *
     * @param key
     * @return
     */
    String get(final String key);

    /**
     * 字符串类型:存入key-value对象，如果key存在，那么默认更新value
     *
     * @param key
     * @param value
     * @return
     */
    Boolean set(final String key, final String value);

    /**
     * 字符串类型:通过key删除对应的key和value
     *
     * @param key
     * @return
     */

    Long delete(final String key);

    /**
     * 字符串类型:通过key判断对象是否存在
     *
     * @param key
     * @return
     */
    Boolean exists(final String key);


    /**
     * 字符串类型:
     * 对存储在指定key的数值执行原子的加1操作。
     * 如果指定的key不存在，那么在执行incr操作之前，会先把它的值设定为0.
     * 如果指定的key中存储的值不是字符串类型或者存储的字符串类型不能表示为一个整数，那么执行这个命令时服务器会返回一个错误。
     * 注意：由于redis并没有一个明确的类型来表示整型数据，所以这个操作是一个字符串操作。
     * 执行这个操作的时候，key对应存储的字符串被解析为10进制的64位有符号整型数据。
     * 这个操作仅限于64位的有符号整型数据。
     * 事实上，redis内部采用整数形式来存储对应的整数值，所以对该类字符串值实际上是用整数保存，也就不存在存储整数的字符串表示所带来的额外消耗。
     * incr的原子操作是说即使多个客户端对同一个key发出incr命令，也决不会导致竞争的情况，
     * 例如如下情况永远不可能发生：客户端1和客户端2同时读出10，他们俩都对其加到11，然后将新值设置为11，最终值一定为12
     *
     * @param key
     * @return
     */
    Long incr(final String key);

    /**
     * 字符串类型:
     * 对key对应的数字做减一操作。如果key不存在，那么在操作之前，这个key对应的值会被设定为0。
     * 如果key有一个错误类型的value或者是一个不能表示成数字的字符串，就返回错误。这个操作最大支持在64位有符号的整型数字。
     *
     * @param key
     * @return
     */
    Long decr(final String key);

    /**
     * 字符串类型:
     * 将key进行递增。如果key不存在，操作之前，key就会被置为0.如果key的value类型错误或者是个不能表示成数字的字符串，就返回错误。
     * 这个操作最多支持64位有符号的整形数字。
     *
     * @param key
     * @param offset
     * @return
     */
    Long incrby(final String key, final Long offset);

    /**
     * 字符串类型:
     * 将key对应的数字递减。如果key不存在，操作之前，key就会被置为0.如果key的value类型错误或者是个不能表示成数字的字符串，就返回错误。
     * 这个操作最多支持64位有符号的整形数字。
     *
     * @param key
     * @param offset
     * @return
     */
    Long decrby(final String key, final Long offset);


    /**
     * list列表类型:先进后出栈形式,单个值插入
     *
     * @param key
     * @param value
     * @return
     */
    Long lpush(final String key, final String value);


    String lpop(final String key);

    /**
     * list列表类型:先进先出队列形式,单个值插入
     *
     * @param key
     * @param value
     * @return
     */
    Long rpush(final String key, final String value);

    String rpop(final String key);

    /**
     * list列表类型:
     * 返回存储在key的列表里指定范围内的元素。Start和end偏移量都是基于0的下标，
     * 即list的第一个元素下标是0(list的开头)，第二个元素是下标1，以此类推。
     * 偏移量也可以是负数，表示偏移量是从list尾部开始计数。例如，-1表示列表的最后一个元素，-2是倒数第二个，以此类推。
     *
     * @param key
     * @param start
     * @param end
     * @return
     */


    /**
     * list列表类型:返回list中的元素个数
     *
     * @param key
     * @return
     */
    Long llen(final String key);


    /**
     * set无序集合类型:
     * 添加一个或多个指定的member元素到集合的key中
     *
     * @param key
     * @param  value
     * @return
     */
    Long sadd(final String key,final String value);

    String sPop(final String key);

    Long scard(final String key);


    /**
     * hash类型:
     * 设置 key 指定的哈希集中指定字段的值
     * 如果 key 指定的哈希集不存在，会创建一个新的哈希集并与 key 关联
     * 如果字段在哈希集中存在，它将被重写
     *
     * @param key
     * @param field
     * @param value
     * @return
     */

    Boolean hset(final String key, final String field, final String value);

    /**
     * hash类型:返回 key 指定的哈希集中该字段所关联的值
     *
     * @param key
     * @param field
     * @return
     */

    String hget(final String key, final String field);



    /**
     * hash类型:从 key 指定的哈希集中移除指定的域。
     *
     * @param key
     * @param field
     * @return
     */
    Long hdel(final String key, final String field);



    /**
     * hash类型:返回hash里面key是否存在的标志
     *
     * @param key
     * @param field
     * @return
     */

    Boolean hexists(final String key, final String field);




}
