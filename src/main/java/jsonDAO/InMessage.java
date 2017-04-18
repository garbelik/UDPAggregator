package jsonDAO;


import java.util.List;

public class InMessage {
    private String inSequenceNumber;
    private List<Order> messages;

    public InMessage() {
    }

    public String getInSequenceNumber() {
        return inSequenceNumber;
    }

    public void setInSequenceNumber(String inSequenceNumber) {
        this.inSequenceNumber = inSequenceNumber;
    }

    public List<Order> getMessages() {
        return messages;
    }

    public void setMessages(List<Order> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Message{" +
                "inSequenceNumber=" + inSequenceNumber +
                ", messages=" + messages +
                '}';
    }
}
