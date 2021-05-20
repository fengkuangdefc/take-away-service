package com.chenkesi.order.transaction.domain.entity;

import java.util.Date;

public class TransMessage extends TransMessageKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trans_message.type
     *
     * @mbggenerated
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trans_message.exchange
     *
     * @mbggenerated
     */
    private String exchange;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trans_message.routingKey
     *
     * @mbggenerated
     */
    private String routingkey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trans_message.queue
     *
     * @mbggenerated
     */
    private String queue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trans_message.sequence
     *
     * @mbggenerated
     */
    private Integer sequence;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trans_message.date
     *
     * @mbggenerated
     */
    private Date date;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trans_message.payload
     *
     * @mbggenerated
     */
    private String payload;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trans_message.type
     *
     * @return the value of trans_message.type
     *
     * @mbggenerated
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trans_message.type
     *
     * @param type the value for trans_message.type
     *
     * @mbggenerated
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trans_message.exchange
     *
     * @return the value of trans_message.exchange
     *
     * @mbggenerated
     */
    public String getExchange() {
        return exchange;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trans_message.exchange
     *
     * @param exchange the value for trans_message.exchange
     *
     * @mbggenerated
     */
    public void setExchange(String exchange) {
        this.exchange = exchange == null ? null : exchange.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trans_message.routingKey
     *
     * @return the value of trans_message.routingKey
     *
     * @mbggenerated
     */
    public String getRoutingkey() {
        return routingkey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trans_message.routingKey
     *
     * @param routingkey the value for trans_message.routingKey
     *
     * @mbggenerated
     */
    public void setRoutingkey(String routingkey) {
        this.routingkey = routingkey == null ? null : routingkey.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trans_message.queue
     *
     * @return the value of trans_message.queue
     *
     * @mbggenerated
     */
    public String getQueue() {
        return queue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trans_message.queue
     *
     * @param queue the value for trans_message.queue
     *
     * @mbggenerated
     */
    public void setQueue(String queue) {
        this.queue = queue == null ? null : queue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trans_message.sequence
     *
     * @return the value of trans_message.sequence
     *
     * @mbggenerated
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trans_message.sequence
     *
     * @param sequence the value for trans_message.sequence
     *
     * @mbggenerated
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trans_message.date
     *
     * @return the value of trans_message.date
     *
     * @mbggenerated
     */
    public Date getDate() {
        return date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trans_message.date
     *
     * @param date the value for trans_message.date
     *
     * @mbggenerated
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trans_message.payload
     *
     * @return the value of trans_message.payload
     *
     * @mbggenerated
     */
    public String getPayload() {
        return payload;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trans_message.payload
     *
     * @param payload the value for trans_message.payload
     *
     * @mbggenerated
     */
    public void setPayload(String payload) {
        this.payload = payload == null ? null : payload.trim();
    }
}