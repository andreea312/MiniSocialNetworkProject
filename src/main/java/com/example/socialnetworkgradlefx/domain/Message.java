package com.example.socialnetworkgradlefx.domain;


public class Message extends Entity{
    private final int sender;
    private final int receiver;

    private final String dataSent;
    private final String messageText;

    /**
     * Message constructor
     * @param id Integer - message id
     * @param sender - id of sender
     * @param receiver - id of receiver
     * @param dataSent - date and time when te message was sent
     * @param messageText - text of message
     */
    public Message(int id, int sender, int receiver, String dataSent, String messageText) {
        super(id);
        this.sender = sender;
        this.receiver = receiver;
        this.dataSent = dataSent;
        this.messageText = messageText;
    }

    /**
     * Getter for sender id
     * @return int
     */
    public int getSender() {
        return sender;
    }

    /**
     * Getter for receiver id
     * @return int
     */
    public int getReceiver() {return receiver;}

    /**
     * Getter for the date and time when the message was sent
     * @return String
     */
    public String getDataSent() {
        return dataSent;
    }


    /**
     * Getter for the text of the message
     * @return String
     */
    public String getMessageText() {
        return messageText;
    }
}
