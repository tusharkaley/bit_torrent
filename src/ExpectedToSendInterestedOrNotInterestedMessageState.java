import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.concurrent.ConcurrentHashMap;

public class ExpectedToSendInterestedOrNotInterestedMessageState implements PeerState {
    ConcurrentHashMap<Integer, NeighbourPeerInfo> neighbourConnectionInfo;
    BitSet theirBitfield;
    boolean setState;

    public ExpectedToSendInterestedOrNotInterestedMessageState(ConcurrentHashMap<Integer, NeighbourPeerInfo> neighbourConnectionInfo, BitSet theirBitfield, boolean setState) {
        this.neighbourConnectionInfo = neighbourConnectionInfo;
        this.theirBitfield = theirBitfield;
        this.setState = setState;
    }

    @Override
    public void handleMessage(Handler context, SelfPeerInfo myPeerInfo, DataInputStream inputStream, DataOutputStream outputStream) {
        try {
            this.theirBitfield.xor(myPeerInfo.getBitField());
            if (this.theirBitfield.isEmpty()) {
                ActualMessage actualMessage = new ActualMessage(MessageType.NOT_INTERESTED);
                myPeerInfo.log( "[PEER:" + myPeerInfo.getPeerID() + "]Sent NOT INTERESTED message to " + context.getTheirPeerId());

                outputStream.write(actualMessage.serialize());
                outputStream.flush();


            } else {
                ActualMessage actualMessage = new ActualMessage(MessageType.INTERESTED);
                myPeerInfo.log( "[PEER:" + myPeerInfo.getPeerID() + "]Sent INTERESTED message to " + context.getTheirPeerId());

                outputStream.write(actualMessage.serialize());
                outputStream.flush();

            }

            if (this.setState) {
                context.setState(new WaitForAnyMessageState(neighbourConnectionInfo), true, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
