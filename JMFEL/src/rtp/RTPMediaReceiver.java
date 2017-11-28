package rtp;
import datasource.DataSourceMerger;
import java.net.*;
import java.util.Observable;
import java.util.Vector;
import javax.media.rtp.*;
import javax.media.rtp.event.*;
import javax.media.protocol.DataSource;
import javax.media.control.BufferControl;
import simple_player.SimplePlayer;

public class RTPMediaReceiver extends Observable{
    private String session = null;
    private RTPManager sessionManager = null;
    private int numExpectedStreams = 0;
    private int bufferLenght = 5;
    private Vector<DataSource> dataSources = new Vector();
    private RTPReceiveStreamListener rtpReceiveStreamListener;
    private RTPSessionListener rtpSessionListener;
    private int secondsToWaitForStreams = 1;
    
    public RTPMediaReceiver(String aSession) {
        session = aSession;
    }
    
    public int getSecondsToWaitForStreams() {
        return secondsToWaitForStreams;
    }
    
    public void setSecondsToWaitForStreams(int secondsToWaitForStreams) {
        this.secondsToWaitForStreams = secondsToWaitForStreams;
    }
    
    public int getBufferLenght() {
        return bufferLenght;
    }
    
    public int getNumConnectedStreams() {
        return dataSources.size();
    }
    
    public Vector<DataSource> getDataSources(){
        return dataSources;
    }
    
    public void waitforExpectedStreams(){
        int secs = 0;
        try{
            while(getNumConnectedStreams()!=getNumExpectedStreams()||secs<getSecondsToWaitForStreams()){
                Thread.sleep(1000);
                secs++;
            }
        }catch(Exception e){System.out.println(e);};
    }
    
    public void addDataSource(DataSource aDataSource){
        dataSources.add(aDataSource);
        setChanged();
        notifyObservers();
    }
    
    public void setBufferLenght(int bufferLenght) {
        this.bufferLenght = bufferLenght;
    }
    
    public RTPManager getSessionManager() {
        return sessionManager;
    }
    
    public void setNumExpectedStreams(int numExpectedStreams) {
        this.numExpectedStreams = numExpectedStreams;
    }
    
    public int getNumExpectedStreams() {
        return numExpectedStreams;
    }
    
    public void start(){
        initialize();
    }
    
    private void initialize() {
        try {
            InetAddress ipAddr;
            SessionAddress localAddr = new SessionAddress();
            SessionAddress destAddr;
            RTPSessionInfo sessionInfo = null ;
            rtpReceiveStreamListener = new RTPReceiveStreamListener();
            rtpReceiveStreamListener.setRtpReceiver(this);
            rtpSessionListener = new RTPSessionListener();
            
            try {
                sessionInfo = new RTPSessionInfo(session);
            } catch (IllegalArgumentException e) {
                System.out.println("Failed to parse the session address given: " + session);
            }
            
            System.out.println("  - Open RTP session for: addr: " + sessionInfo.getAddress() + " port: " + sessionInfo.getPort() + " ttl: " + sessionInfo.getTTL());
            
            sessionManager = (RTPManager) RTPManager.newInstance();
            sessionManager.addSessionListener(rtpSessionListener);
            sessionManager.addReceiveStreamListener(rtpReceiveStreamListener);
            
            ipAddr = InetAddress.getByName(sessionInfo.getAddress());
            
            if( ipAddr.isMulticastAddress()) {
                // local and remote address pairs are identical:
                localAddr= new SessionAddress( ipAddr,
                        sessionInfo.getPort(),
                        sessionInfo.getTTL());
                destAddr = new SessionAddress( ipAddr,
                        sessionInfo.getPort(),
                        sessionInfo.getTTL());
            } else {
                localAddr= new SessionAddress( InetAddress.getLocalHost(),
                        sessionInfo.getPort());
                destAddr = new SessionAddress( ipAddr, sessionInfo.getPort());
            }
            
            sessionManager.initialize(localAddr);
            BufferControl bc = (BufferControl)sessionManager.getControl("javax.media.control.BufferControl");
            if (bc != null)
                bc.setBufferLength(getBufferLenght());
            sessionManager.addTarget(destAddr);
        } catch (Exception e){
            System.out.println("Cannot create the RTP Session: " + e.getMessage());
        }
        
        long then = System.currentTimeMillis();
        long waitingPeriod = 30000;
        
        try{
            synchronized (rtpReceiveStreamListener.getDataSync()) {
                while (!rtpReceiveStreamListener.isDataReceived() &&
                        System.currentTimeMillis() - then < waitingPeriod) {
                    if (!rtpReceiveStreamListener.isDataReceived())
                        System.out.println("  - Waiting for RTP data to arrive...");
                    rtpReceiveStreamListener.getDataSync().wait(1000);
                }
            }
        } catch (Exception e) {}
        
        if (!rtpReceiveStreamListener.isDataReceived()) {
            System.out.println("No RTP data was received.");
            close();
        }
    }
    
    private void close() {
        
        if (sessionManager != null) {
            sessionManager.removeTargets( "Closing session from AVReceive2");
            sessionManager.dispose();
            sessionManager = null;
        }
    }
}

class RTPSessionInfo {
    
    private String address = null;
    private int port;
    private int ttl = 1;
    
    public int getPort() {
        return port;
    }
    
    public String getAddress() {
        return address;
    }
    
    public int getTTL() {
        return ttl;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setPort(int port) {
        this.port = port;
    }
    
    public void setTTL(int ttl) {
        this.ttl = ttl;
    }
    
    RTPSessionInfo(String session) throws IllegalArgumentException {
        int off;
        String portStr = null, ttlStr = null;
        
        if (session != null && session.length() > 0) {
            while (session.length() > 1 && session.charAt(0) == '/')
                session = session.substring(1);
            
            // Now see if there's a addr specified.
            off = session.indexOf('/');
            if (off == -1) {
                if (!session.equals(""))
                    address = session;
            } else {
                address = session.substring(0, off);
                session = session.substring(off + 1);
                // Now see if there's a port specified
                off = session.indexOf('/');
                if (off == -1) {
                    if (!session.equals(""))
                        portStr = session;
                } else {
                    portStr = session.substring(0, off);
                    session = session.substring(off + 1);
                    // Now see if there's a ttl specified
                    off = session.indexOf('/');
                    if (off == -1) {
                        if (!session.equals(""))
                            ttlStr = session;
                    } else {
                        ttlStr = session.substring(0, off);
                    }
                }
            }
        }
        
        if (address == null)
            throw new IllegalArgumentException();
        
        if (portStr != null) {
            try {
                Integer integer = Integer.valueOf(portStr);
                if (integer != null)
                    port = integer.intValue();
            } catch (Throwable t) {
                throw new IllegalArgumentException();
            }
        } else
            throw new IllegalArgumentException();
        
        if (ttlStr != null) {
            try {
                Integer integer = Integer.valueOf(ttlStr);
                if (integer != null)
                    ttl = integer.intValue();
            } catch (Throwable t) {
                throw new IllegalArgumentException();
            }
        }
    }
}

class RTPReceiveStreamListener implements ReceiveStreamListener{
    private RTPMediaReceiver rtpReceiver;
    private DataSource dataSource;
    private boolean dataReceived = false;
    private Object dataSync = new Object();
    
    public RTPReceiveStreamListener(){
    }
    
    public Object getDataSync() {
        return dataSync;
    }
    
    public boolean isDataReceived() {
        return dataReceived;
    }
    
    public RTPMediaReceiver getRtpReceiver() {
        return rtpReceiver;
    }
    
    public void setRtpReceiver(RTPMediaReceiver rtpReceiver) {
        this.rtpReceiver = rtpReceiver;
    }
    
    public synchronized void update( ReceiveStreamEvent evt) {
        
        RTPManager mgr = (RTPManager)evt.getSource();
        Participant participant = evt.getParticipant();
        ReceiveStream stream = evt.getReceiveStream();
        
        if (evt instanceof RemotePayloadChangeEvent) {
            
            System.out.println("  - Received an RTP PayloadChangeEvent.");
            System.out.println("Sorry, cannot handle payload change.");
            System.exit(0);
            
        }
        
        else if (evt instanceof NewReceiveStreamEvent) {
            
            try {
                stream = ((NewReceiveStreamEvent)evt).getReceiveStream();
                dataSource = stream.getDataSource();
                
                // Find out the formats.
                RTPControl ctl = (RTPControl)dataSource.getControl("javax.media.rtp.RTPControl");
                if (ctl != null){
                    System.out.println("  - Recevied new RTP stream: " + ctl.getFormat());
                } else
                    System.out.println("  - Received new RTP stream");
                
                if (participant == null)
                    System.out.println("      The sender of this stream had yet to be identified.");
                else {
                    System.out.println("      The stream comes from: " + participant.getCNAME());
                }
                
                // Notify intialize() that a new stream had arrived.
                synchronized (dataSync) {
                    dataReceived = true;
                    dataSync.notifyAll();
                }
                
            } catch (Exception e) {
                System.out.println("NewReceiveStreamEvent exception " + e.getMessage());
                return;
            }
            
        }
        
        else if (evt instanceof StreamMappedEvent) {
            
            if (stream != null && stream.getDataSource() != null) {
                dataSource = stream.getDataSource();
                // Find out the formats.
                RTPControl ctl = (RTPControl)dataSource.getControl("javax.media.rtp.RTPControl");
                System.out.println("  - The previously unidentified stream ");
                if (ctl != null)
                    System.out.println("      " + ctl.getFormat());
                System.out.println("      had now been identified as sent by: " + participant.getCNAME());
                getRtpReceiver().addDataSource(dataSource);
            }
        }
        
        else if (evt instanceof ByeEvent) {
            
            System.out.println("  - Got \"bye\" from: " + participant.getCNAME());
        }
    }
}


class RTPSessionListener implements SessionListener{
    public RTPSessionListener(){
    }
    
    public synchronized void update(SessionEvent evt) {
        if (evt instanceof NewParticipantEvent) {
            Participant p = ((NewParticipantEvent)evt).getParticipant();
            System.out.println("  - A new participant had just joined: " + p.getCNAME());
        }
    }
}
