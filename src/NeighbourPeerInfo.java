import java.util.BitSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NeighbourPeerInfo extends PeerInfo {
    // In context of when Peer is a neighbour
    private Double downloadSpeed;
    private NeighbourState neighbourState;
    private Handler context;
    private int requestedPieceIndex;
    private boolean receivedLastBitfieldAck;

    public NeighbourPeerInfo(PeerInfo.Builder b, Handler context) {
        super(b);
        this.context = context;
        this.downloadSpeed = 0.0;
        this.neighbourState = NeighbourState.UNKNOWN;
        this.requestedPieceIndex = -1;
        this.receivedLastBitfieldAck = false;
    }

    public int getRequestedPieceIndex() {
        return requestedPieceIndex;
    }

    public void setRequestedPieceIndex(int requestedPieceIndex) {

        this.requestedPieceIndex = requestedPieceIndex;
    }

    public NeighbourState getNeighbourState() {
        return neighbourState;
    }

    public void setNeighbourState(NeighbourState neighbourState) {
        this.neighbourState = neighbourState;
    }

    public boolean isInterested() {
        if (neighbourState == NeighbourState.CHOKED_AND_INTERESTED ||
                neighbourState == NeighbourState.UNCHOKED_AND_INTERESTED) {
            return true;
        }
        return false;
    }

    public boolean isChokedAndInterested() {
        if (neighbourState == NeighbourState.CHOKED_AND_INTERESTED) {
            return true;
        }
        return false;
    }

    public boolean isChokedAndNotInterested() {
        if (neighbourState == NeighbourState.CHOKED_AND_NOT_INTERESTED) {
            return true;
        }
        return false;
    }

    public boolean isChoked() {
        if (neighbourState == NeighbourState.CHOKED_AND_INTERESTED ||
                neighbourState == NeighbourState.CHOKED_AND_NOT_INTERESTED) {
            return true;
        }
        return false;
    }
    public boolean shutdown(){
        if(neighbourState == NeighbourState.SHUTDOWN){
            return true;
        }
        return false;
    }

    public boolean isUnChoked() {
        if (neighbourState != NeighbourState.CHOKED_AND_NOT_INTERESTED && neighbourState != NeighbourState.CHOKED_AND_INTERESTED) {
            return true;
        }
        return false;
    }

    public boolean isUnknown() {
        if (neighbourState == NeighbourState.UNKNOWN) {
            return true;
        }
        return false;
    }

    public Double getDownloadSpeed() {
        return this.downloadSpeed;
    }

    public void setDownloadSpeed(Double downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    public Handler getContext() {
        return context;
    }

    public void setContext(Handler context) {
        this.context = context;
    }

    public void setContextState(PeerState newState, boolean isForInputState, boolean setOtherStateAsNull) {
        this.context.setState(newState, isForInputState, setOtherStateAsNull);
    }

    public boolean hasReceivedLastBitfieldAck() {
        return receivedLastBitfieldAck;
    }

    public void setReceivedLastBitfieldAck(boolean receivedLastBitfieldAck) {
        this.receivedLastBitfieldAck = receivedLastBitfieldAck;
    }
}
