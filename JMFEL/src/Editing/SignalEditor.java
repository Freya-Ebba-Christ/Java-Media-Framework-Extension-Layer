package Editing;
import custom_controller.CustomController;
import datasource.SignalDataSource;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.EventObject;
import java.util.Vector;
import javax.media.*;
import javax.media.protocol.*;
import javax.media.control.TrackControl;
import javax.media.format.*;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import plugins.codecs.BasicAudioCodec;
import plugins.video.VideoDataAccessor;
import utilities.DoubleDataBuffer;
import utilities.graphics.passive_rendering.PassiveRenderingPanel;
import utilities.graphics.passive_rendering.Surface;
import visual_signal_components.passive_rendering.SignalPanel;

public class SignalEditor extends JFrame{
    private CustomController controllerListener = null;
    private DataSource inputDataSource = null;
    private DataSource outputDataSource = null;
    private Codec[] codecs = null;
    private Processor processor = null;
    private Component jmf_controls;
    private ControlsButtonListener controlsButtonListener;
    private TableEditingButtonListener tableEditingButtonListener;
    private Codec[] videoFrameAccessCodecs;
    private Codec[] audioFrameAccessCodecs;
    private JTable table;
    private RangePanel rangePanel;
    private double defaultRange = 10.0;
    
    private JButton ADDCLASS_Button;
    private JButton CLEARALL_Button;
    private JButton GENERATETRAININGSET_Button;
    private JButton REMOVECLASS_Button;
    private JButton EDITCLASS_Button;
    private JLabel stateLabel;
    private JMenuBar applicationMenuBar;
    private JPanel auxillaryDataPanel;
    private JPanel buttonContainerPanel;
    private JPanel buttonPanel1;
    private JPanel buttonPanel2;
    private JButton bwd1000Button;
    private JButton bwd100Button;
    private JButton bwd10Button;
    private JPanel editorPanel;
    private JPanel framePositioningControlsPanel;
    private JButton fwd1000Button;
    private JButton fwd100Button;
    private JButton fwd10Button;
    private JPanel fdw_bwdButtonPanel;
    private JScrollPane signalPanel;
    private JPanel signalsContentPanel;
    private JPanel rootContentPanel;
    private JPanel signalPanelContainer;
    private JPanel tableContentPanel;
    private JPanel videoContainer;
    private JPanel videoControlsPanel;
    private JPanel videoPanel;
    private RangesEditor rangesEditor;
    private boolean hasVideo = false;
    
    private int numchannels;
    private int samplerate;
    public SignalEditor() {
    }
    
    public SignalEditor(DataSource aDataSource) {
        inputDataSource = aDataSource;
        initCodecs();
        createProcessor();
        initVisualSignalComponent();
        
        String[] classLabels = {"Stage1","Stage2","Stage3","Stage4"};
        
        setUpTable(classLabels);
        initCodecs();
        initListeners();
        // the rangePanel was created and set up in the initVisualSignalComponent() method
        // the the model was set up later in the setUpTable() method
        getRangePanel().setModel((ClassesTableModel)table.getModel());
        // the rangePanel draws the ranges stored in the table model
        getRangePanel().start();
    }
    
    public JPanel getSignalsContentPanel() {
        return signalsContentPanel;
    }
    
    public void setSignalsContentPanel(JPanel signalsContentPanel) {
        this.signalsContentPanel = signalsContentPanel;
    }
    
    public boolean hasVideo() {
        return hasVideo;
    }
    
    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }
    
    public void initCodecs(){
        videoFrameAccessCodecs = new Codec[1];
        videoFrameAccessCodecs[0] = new VideoFrameAccessor();
        ((VideoFrameAccessor)videoFrameAccessCodecs[0]).setSignalEditor(this);
        setVideoFrameAccessCodecs(videoFrameAccessCodecs);
        
        audioFrameAccessCodecs = new Codec[1];
        audioFrameAccessCodecs[0] = new AudioFrameAccessor();
        ((AudioFrameAccessor)audioFrameAccessCodecs[0]).setSignalEditor(this);
        setAudioFrameAccessCodecs(audioFrameAccessCodecs);
    }
    
    public void initListeners(){
        //add local ButtonListener to forward and backward buttons
        setControlsButtonListener(new ControlsButtonListener(this));
        setUpControlsButtonListener();
        //add local ButtonListener to enable editing the table
        setupTableEditingButtonListener();
    }
    
    public void setAudioFrameAccessCodecs(Codec[] audioFrameAccessCodecs) {
        this.audioFrameAccessCodecs = audioFrameAccessCodecs;
    }
    
    public Codec[] getAudioFrameAccessCodecs() {
        return audioFrameAccessCodecs;
    }
    
    public JPanel getAuxillaryDataPanel() {
        return auxillaryDataPanel;
    }
    
    public JMenuBar getApplicationMenuBar() {
        return applicationMenuBar;
    }
    
    public JPanel getButtonContainerPanel() {
        return buttonContainerPanel;
    }
    
    public Codec[] getCodecs() {
        return codecs;
    }
    
    public CustomController getControllerListener() {
        return controllerListener;
    }
    
    public ControlsButtonListener getControlsButtonListener() {
        return controlsButtonListener;
    }
    
    public double getDefaultRange() {
        return defaultRange;
    }
    
    public JPanel getEditorPanel() {
        return editorPanel;
    }
    
    public JPanel getButtonPanel1() {
        return buttonPanel1;
    }
    
    public JPanel getButtonPanel2() {
        return buttonPanel2;
    }
    
    public JButton getBwd1000Button() {
        return bwd1000Button;
    }
    
    public JButton getBwd100Button() {
        return bwd100Button;
    }
    
    public JButton getBwd10Button() {
        return bwd10Button;
    }
    
    public JPanel getFdw_bwdButtonPanel() {
        return fdw_bwdButtonPanel;
    }
    
    public JPanel getFramePositioningControlsPanel() {
        return framePositioningControlsPanel;
    }
    
    public JButton getFwd1000Button() {
        return fwd1000Button;
    }
    
    public JButton getFwd100Button() {
        return fwd100Button;
    }
    
    public JButton getFwd10Button() {
        return fwd10Button;
    }
    
    public DataSource getInputDataSource() {
        return inputDataSource;
    }
    
    public Codec[] getVideoFrameAccessCodecs() {
        return videoFrameAccessCodecs;
    }
    
    public JScrollPane getSignalPanel() {
        return signalPanel;
    }
    
    public Component getJmf_controls() {
        return jmf_controls;
    }
    
    public DataSource getOutputDataSource() {
        return outputDataSource;
    }
    
    public JPanel getRootContentPanel() {
        return rootContentPanel;
    }
    
    public JPanel getSignalPanelContainer() {
        return signalPanelContainer;
    }
    
    public JLabel getStateLabel() {
        return stateLabel;
    }
    
    public JPanel getTableContentPanel() {
        return tableContentPanel;
    }
    
    public TableEditingButtonListener getTableEditingButtonListener() {
        return tableEditingButtonListener;
    }
    
    public JPanel getVideoContainer() {
        return videoContainer;
    }
    
    public JPanel getVideoControlsPanel() {
        return videoControlsPanel;
    }
    
    public JPanel getVideoPanel() {
        return videoPanel;
    }
    
    public void setADDCLASS_Button(JButton ADDCLASS_Button) {
        this.ADDCLASS_Button = ADDCLASS_Button;
    }
    
    public void setApplicationMenuBar(JMenuBar applicationMenuBar) {
        this.applicationMenuBar = applicationMenuBar;
    }
    
    public void setAuxillaryDataPanel(JPanel auxillaryDataPanel) {
        this.auxillaryDataPanel = auxillaryDataPanel;
    }
    
    public void setButtonContainerPanel(JPanel buttonContainerPanel) {
        this.buttonContainerPanel = buttonContainerPanel;
    }
    
    public void setButtonPanel1(JPanel buttonPanel1) {
        this.buttonPanel1 = buttonPanel1;
    }
    
    public void setButtonPanel2(JPanel buttonPanel2) {
        this.buttonPanel2 = buttonPanel2;
    }
    
    public void setBwd1000Button(JButton bwd1000Button) {
        this.bwd1000Button = bwd1000Button;
    }
    
    public void setBwd100Button(JButton bwd100Button) {
        this.bwd100Button = bwd100Button;
    }
    
    public void setBwd10Button(JButton bwd10Button) {
        this.bwd10Button = bwd10Button;
    }
    
    public void setCLEARALL_Button(JButton CLEARALL_Button) {
        this.CLEARALL_Button = CLEARALL_Button;
    }
    
    public void setCodecs(Codec[] codecs) {
        this.codecs = codecs;
    }
    
    public void setControllerListener(CustomController controllerListener) {
        this.controllerListener = controllerListener;
    }
    
    public void setControlsButtonListener(ControlsButtonListener controlsButtonListener) {
        this.controlsButtonListener = controlsButtonListener;
    }
    
    public void setDefaultRange(double defaultRange) {
        this.defaultRange = defaultRange;
    }
    
    public void setEDITCLASS_Button(JButton EDITCLASS_Button) {
        this.EDITCLASS_Button = EDITCLASS_Button;
    }
    
    public void setEditorPanel(JPanel editorPanel) {
        this.editorPanel = editorPanel;
    }
    
    public void setFdw_bwdButtonPanel(JPanel fdw_bwdButtonPanel) {
        this.fdw_bwdButtonPanel = fdw_bwdButtonPanel;
    }
    
    public void setFramePositioningControlsPanel(JPanel framePositioningControlsPanel) {
        this.framePositioningControlsPanel = framePositioningControlsPanel;
    }
    
    public void setFwd1000Button(JButton fwd1000Button) {
        this.fwd1000Button = fwd1000Button;
    }
    
    public void setFwd100Button(JButton fwd100Button) {
        this.fwd100Button = fwd100Button;
    }
    
    public void setFwd10Button(JButton fwd10Button) {
        this.fwd10Button = fwd10Button;
    }
    
    public void setGENERATETRAININGSET_Button(JButton GENERATETRAININGSET_Button) {
        this.GENERATETRAININGSET_Button = GENERATETRAININGSET_Button;
    }
    
    public void setInputDataSource(DataSource inputDataSource) {
        this.inputDataSource = inputDataSource;
    }
    
    public void setVideoFrameAccessCodecs(Codec[] internalVideoFrameAccessCodec) {
        this.videoFrameAccessCodecs = internalVideoFrameAccessCodec;
    }
    
    public void setSignalPanel(JScrollPane signalPanel) {
        this.signalPanel = signalPanel;
    }
    
    public void setJmf_controls(Component jmf_controls) {
        this.jmf_controls = jmf_controls;
    }
    
    public void setOutputDataSource(DataSource outputDataSource) {
        this.outputDataSource = outputDataSource;
    }
    
    public void setREMOVECLASS_Button(JButton REMOVECLASS_Button) {
        this.REMOVECLASS_Button = REMOVECLASS_Button;
    }
    
    public void setRangePanel(RangePanel rangePanel) {
        this.rangePanel = rangePanel;
    }
    
    public void setRootContentPanel(JPanel rootContentPanel) {
        this.rootContentPanel = rootContentPanel;
    }
    
    public void setSignalPanelContainer(JPanel signalPanel) {
        this.signalPanelContainer = signalPanel;
    }
    
    public void setStateLabel(JLabel stateLabel) {
        this.stateLabel = stateLabel;
    }
    
    public void setTable(JTable table) {
        this.table = table;
    }
    
    public void setTableContentPanel(JPanel tableContentPanel) {
        this.tableContentPanel = tableContentPanel;
    }
    
    public void setTableEditingButtonListener(TableEditingButtonListener tableEditingButtonListener) {
        this.tableEditingButtonListener = tableEditingButtonListener;
    }
    
    public void setVideoContainer(JPanel videoContainer) {
        this.videoContainer = videoContainer;
    }
    
    public void setVideoControlsPanel(JPanel videoControlsPanel) {
        this.videoControlsPanel = videoControlsPanel;
    }
    
    public void setVideoPanel(JPanel videoPanel) {
        this.videoPanel = videoPanel;
    }
    
    public void start() {
        getProcessor().start();
    }
    
    public void stop() {
        getProcessor().stop();
    }
    
    public void setMute(boolean aValue){
        getProcessor().getGainControl().setMute(aValue);
    }
    
    public void close() {
        getProcessor().close();
    }
    
    public void start(Time aTime) {
        getProcessor().syncStart(aTime);
    }
    
    public Time getSyncTime(){
        return getProcessor().getSyncTime();
    }
    
    public TimeBase getTimeBase(){
        return getProcessor().getTimeBase();
    }
    
    public void setTimeBase(TimeBase aTimeBase){
        try{
            getProcessor().setTimeBase(aTimeBase);
        }catch(Exception ex){System.out.println(ex);
        }
    }
    
    public void setDataSource(DataSource aDataSource){
        inputDataSource = aDataSource;
        createProcessor();
        initVisualSignalComponent();
    }
    
    public DataSource getDataSource(){
        try{
            outputDataSource = getProcessor().getDataOutput();
        }catch (Exception ex){System.err.println();
        };
        return outputDataSource;
    }
    
    public Processor getProcessor(){
        return processor;
    }
    
    public void setProcessor(Processor aProcessor){
        processor = aProcessor;
    }
    
    private void createProcessor(){
        try {
            setProcessor(Manager.createProcessor(inputDataSource));
        } catch (Exception e) {
            System.err.println("Failed to create the processor" + e);
        }
        controllerListener = new CustomController(getProcessor());
        getProcessor().addControllerListener(controllerListener);
        
        // Put the Processor into configured state.
        getProcessor().configure();
        if (!controllerListener.waitForState(getProcessor().Configured)) {
            System.err.println("Failed to configure the processor.");
        }
        //so we can use it as a player
        getProcessor().setContentDescriptor(null);
        TrackControl tc[] = getProcessor().getTrackControls();
        if (tc == null) {
            System.err.println("Failed to obtain track controls from the processor.");
        }
        // Search for the track control for the video track.
        Vector<TrackControl> audioTrackVector = new Vector();
        
        for (int i = 0; i < tc.length; i++) {
            if (tc[i].getFormat() instanceof AudioFormat) {
                audioTrackVector.addElement(tc[i]);
                break;
            }
        }
        
        // Search for the track control for the video track.
        Vector<TrackControl> videoTrackVector = new Vector();
        
        for (int i = 0; i < tc.length; i++) {
            if (tc[i].getFormat() instanceof VideoFormat) {
                videoTrackVector.addElement(tc[i]);
                break;
            }
        }
        
        if (videoTrackVector.size() == 0) {
            System.err.println("The input media does not contain a video track.");
        } else{
            setHasVideo(true);
            ((VideoFrameAccessor)videoFrameAccessCodecs[0]).setController(getControllerListener());
            try {
                videoTrackVector.elementAt(0).setCodecChain(getVideoFrameAccessCodecs());
            } catch (UnsupportedPlugInException upex){System.out.println(upex);};
        }
        
        if (audioTrackVector.size() == 0) {
            System.err.println("The input media does not contain an audio track.");
        } else{
            ((AudioFrameAccessor)audioFrameAccessCodecs[0]).setController(getControllerListener());
            try {
                audioTrackVector.elementAt(0).setCodecChain(getAudioFrameAccessCodecs());
            } catch (UnsupportedPlugInException upex){System.out.println(upex);};
        }
        
        // Prefetch the processor.
        getProcessor().prefetch();
        
        if (!controllerListener.waitForState(getProcessor().Prefetched)) {
            System.err.println("Failed to prefetch the processor.");
        }
    }
    
    public RangePanel getRangePanel(){
        return rangePanel;
    }
    
    public void initVisualSignalComponent() {
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.setProperty("sun.java2d.translaccel", "true");
        Component cc;
        Component vc;
        if ((vc = getProcessor().getVisualComponent()) != null) {
            videoPanel.add("Center",vc);
        }
        
        framePositioningControlsPanel.add("Center", getProcessor().getControlPanelComponent());
        
        //add and initialize the rangePanel
        rangePanel = new RangePanel();
        rangePanel.setMinimumSize(new Dimension(framePositioningControlsPanel.getWidth(),framePositioningControlsPanel.getHeight()*2));
        rangePanel.setPreferredSize(new Dimension(framePositioningControlsPanel.getWidth(),framePositioningControlsPanel.getHeight()*2));
        rangePanel.setMaximumSize(new Dimension(32000,framePositioningControlsPanel.getHeight()*2));
        rangePanel.setSize(new Dimension(32000,framePositioningControlsPanel.getHeight()*2));
        rangePanel.setRangeInSeconds(defaultRange);
        rangePanel.setProcessor(getProcessor());
        framePositioningControlsPanel.add("South", rangePanel);
        
        Surface channelsRenderingSurface = new Surface();
        channelsRenderingSurface.setMinimumSize(new Dimension(getSignalsContentPanel().getWidth(),1000));
        channelsRenderingSurface.setPreferredSize(new Dimension(getSignalsContentPanel().getWidth(),1000));
        channelsRenderingSurface.setMaximumSize(new Dimension(32000,1000));
        channelsRenderingSurface.setSize(new Dimension(32000,1000));
        channelsRenderingSurface.setBackground(Color.BLUE);
        channelsRenderingSurface.setCycles(20);
        channelsRenderingSurface.setFPSEnabled(true);
        channelsRenderingSurface.start();
        getSignalsContentPanel().add(channelsRenderingSurface);
    }
    
    private JTable getTable(){
        return table;
    }
    
    private void setUpTable(String[] classLabels){
        table = new JTable(new ClassesTableModel());
        //this listener is also used by the ButtonEditor. To perform actions for buttons within the table
        tableEditingButtonListener = new TableEditingButtonListener(getTable(),this);
        Object[] defaultRow = {"", "","Bookmark", classLabels[0], new Boolean(false), new Object()};
        ((ClassesTableModel)table.getModel()).init(defaultRow);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        
        //Set up column sizes.
        initColumnSizes(table);
        //once the columns have been rezized, remove the row.
        ((ClassesTableModel)table.getModel()).removeRow(0);
        //Fiddle with the Sport column's cell editors/renderers.
        setUpClassesColumn(table, table.getColumnModel().getColumn(3),classLabels);
        setUpBookMarkColumn(table,table.getColumnModel().getColumn(2));
        //Add the scroll pane to this panel.
        tableContentPanel.add("Center",scrollPane);
        
        //hide the first two columns. These columns are used for storing the beginning and the end
        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(3));
    }
    
    private void setUpBookMarkColumn(JTable table, TableColumn classesColumn) {
        ButtonRenderer aButtonRenderer = new ButtonRenderer();
        ButtonEditor aButtonEditor = new ButtonEditor();
        aButtonEditor.setActionCommand(ButtonIDs.TOSELECTEDCLASS);
        aButtonEditor.addActionListener(tableEditingButtonListener);
        classesColumn.setCellEditor(aButtonEditor);
        classesColumn.setCellRenderer(aButtonRenderer);
    }
    
    private void setUpClassesColumn(JTable table,
            TableColumn classesColumn, String[] classLabels) {
        //Set up the editor for the stage cells.
        JComboBox comboBox = new JComboBox();
        for (int i = 0; i < classLabels.length; i++) {
            comboBox.addItem(classLabels[i]);
        }
        classesColumn.setCellEditor(new DefaultCellEditor(comboBox));
        
        //Set up tool tips for the stage cells.
        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();
        renderer.setToolTipText("Click here to select a sleep stage");
        classesColumn.setCellRenderer(renderer);
    }
    
    private void initColumnSizes(JTable table) {
        ClassesTableModel model = (ClassesTableModel)table.getModel();
        TableColumn column = null;
        Component comp = null;
        int headerWidth = 0;
        int cellWidth = 0;
        Object[] longValues = model.getLongValues();
        TableCellRenderer headerRenderer =
                table.getTableHeader().getDefaultRenderer();
        
        for (int i = 0; i < longValues.length; i++) {
            column = table.getColumnModel().getColumn(i);
            comp = headerRenderer.getTableCellRendererComponent(
                    null, column.getHeaderValue(),
                    false, false, 0, 0);
            headerWidth = comp.getPreferredSize().width;
            
            comp = table.getDefaultRenderer(model.getColumnClass(i)).
                    getTableCellRendererComponent(
                    table, longValues[i],
                    false, false, 0, i);
            cellWidth = comp.getPreferredSize().width;
            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
        }
    }
    
    private JButton getBWD10BUTTON(){
        return bwd10Button;
    }
    
    private JButton getBWD100BUTTON(){
        return bwd100Button;
    }
    
    private JButton getBWD1000BUTTON(){
        return bwd1000Button;
    }
    
    private JButton getFWD10BUTTON(){
        return fwd10Button;
    }
    
    private JButton getFWD100BUTTON(){
        return fwd100Button;
    }
    
    private JButton getFWD1000BUTTON(){
        return fwd1000Button;
    }
    
    private JButton getEDITCLASS_Button(){
        return EDITCLASS_Button;
    }
    
    private JButton getGENERATETRAININGSET_Button(){
        return GENERATETRAININGSET_Button;
    }
    
    private JButton getCLEARALL_Button(){
        return CLEARALL_Button;
    }
    
    private JButton getADDCLASS_Button(){
        return ADDCLASS_Button;
    }
    
    private JButton getREMOVECLASS_Button(){
        return REMOVECLASS_Button;
    }
    
    private void setUpControlsButtonListener(){
        
        getBWD10BUTTON().setActionCommand(ButtonIDs.BWD10BUTTON);
        getBWD10BUTTON().addActionListener(controlsButtonListener);
        
        getBWD100BUTTON().setActionCommand(ButtonIDs.BWD100BUTTON);
        getBWD100BUTTON().addActionListener(controlsButtonListener);
        
        getBWD1000BUTTON().setActionCommand(ButtonIDs.BWD1000BUTTON);
        getBWD1000BUTTON().addActionListener(controlsButtonListener);
        
        getFWD10BUTTON().setActionCommand(ButtonIDs.FWD10BUTTON);
        getFWD10BUTTON().addActionListener(controlsButtonListener);
        
        getFWD100BUTTON().setActionCommand(ButtonIDs.FWD100BUTTON);
        getFWD100BUTTON().addActionListener(controlsButtonListener);
        
        getFWD1000BUTTON().setActionCommand(ButtonIDs.FWD1000BUTTON);
        getFWD1000BUTTON().addActionListener(controlsButtonListener);
    }
    
    private void setupTableEditingButtonListener(){
        
        getGENERATETRAININGSET_Button().setActionCommand(ButtonIDs.GENERATETRAININGSET);
        getGENERATETRAININGSET_Button().addActionListener(tableEditingButtonListener);
        
        getCLEARALL_Button().setActionCommand(ButtonIDs.CLEARALL);
        getCLEARALL_Button().addActionListener(tableEditingButtonListener);
        
        getADDCLASS_Button().setActionCommand(ButtonIDs.ADDCLASS);
        getADDCLASS_Button().addActionListener(tableEditingButtonListener);
        
        getREMOVECLASS_Button().setActionCommand(ButtonIDs.REMOVECLASS);
        getREMOVECLASS_Button().addActionListener(tableEditingButtonListener);
        
        getEDITCLASS_Button().setActionCommand(ButtonIDs.EDITCLASS);
        getEDITCLASS_Button().addActionListener(tableEditingButtonListener);
    }
    
    public void initComponents() {
        stateLabel = new JLabel();
        rootContentPanel = new JPanel();
        editorPanel = new JPanel();
        videoContainer = new JPanel();
        videoPanel = new JPanel();
        videoControlsPanel = new JPanel();
        auxillaryDataPanel = new JPanel();
        tableContentPanel = new JPanel();
        buttonContainerPanel = new JPanel();
        buttonPanel1 = new JPanel();
        buttonPanel2 = new JPanel();
        ADDCLASS_Button = new JButton();
        REMOVECLASS_Button = new JButton();
        GENERATETRAININGSET_Button = new JButton();
        CLEARALL_Button = new JButton();
        EDITCLASS_Button = new JButton();
        signalPanelContainer = new JPanel();
        framePositioningControlsPanel = new JPanel();
        fdw_bwdButtonPanel = new JPanel();
        bwd1000Button = new JButton();
        bwd100Button = new JButton();
        bwd10Button = new JButton();
        fwd10Button = new JButton();
        fwd100Button = new JButton();
        fwd1000Button = new JButton();
        signalPanel = new JScrollPane();
        applicationMenuBar = new JMenuBar();
        //initialize the dialog that is used to edit ranges of bookmarks within the table model
        rangesEditor = new RangesEditor(this,true);
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        stateLabel.setText("jLabel1");
        getContentPane().add(stateLabel, java.awt.BorderLayout.SOUTH);
        
        rootContentPanel.setLayout(new java.awt.BorderLayout());
        
        rootContentPanel.setMaximumSize(new java.awt.Dimension(960, 2147483647));
        editorPanel.setLayout(new BoxLayout(editorPanel, BoxLayout.X_AXIS));
        
        editorPanel.setMaximumSize(new java.awt.Dimension(1024, 32767));
        editorPanel.setMinimumSize(new java.awt.Dimension(1024, 240));
        editorPanel.setPreferredSize(new java.awt.Dimension(1024, 266));
        videoContainer.setLayout(new java.awt.BorderLayout());
        
        videoContainer.setMaximumSize(new java.awt.Dimension(320, 240));
        videoContainer.setMinimumSize(new java.awt.Dimension(320, 240));
        videoContainer.setPreferredSize(new java.awt.Dimension(320, 240));
        videoPanel.setLayout(new java.awt.BorderLayout());
        
        videoPanel.setMaximumSize(new java.awt.Dimension(320, 240));
        videoPanel.setMinimumSize(new java.awt.Dimension(320, 240));
        videoPanel.setPreferredSize(new java.awt.Dimension(320, 240));
        videoContainer.add(videoPanel, java.awt.BorderLayout.CENTER);
        
        videoControlsPanel.setLayout(new java.awt.BorderLayout());
        
        videoControlsPanel.setMaximumSize(new java.awt.Dimension(320, 240));
        videoControlsPanel.setMinimumSize(new java.awt.Dimension(320, 0));
        videoControlsPanel.setPreferredSize(new java.awt.Dimension(320, 20));
        videoContainer.add(videoControlsPanel, java.awt.BorderLayout.SOUTH);
        
        editorPanel.add(videoContainer);
        
        auxillaryDataPanel.setLayout(new java.awt.GridBagLayout());
        
        auxillaryDataPanel.setMaximumSize(new java.awt.Dimension(320, 240));
        auxillaryDataPanel.setMinimumSize(new java.awt.Dimension(320, 240));
        auxillaryDataPanel.setPreferredSize(new java.awt.Dimension(320, 240));
        editorPanel.add(auxillaryDataPanel);
        
        tableContentPanel.setLayout(new java.awt.BorderLayout());
        
        tableContentPanel.setMaximumSize(new java.awt.Dimension(32000, 240));
        tableContentPanel.setMinimumSize(new java.awt.Dimension(320, 240));
        tableContentPanel.setPreferredSize(new java.awt.Dimension(320, 240));
        buttonContainerPanel.setLayout(new java.awt.BorderLayout());
        
        buttonContainerPanel.setMaximumSize(new java.awt.Dimension(32000, 2147483647));
        buttonContainerPanel.setMinimumSize(new java.awt.Dimension(320, 66));
        buttonContainerPanel.setPreferredSize(new java.awt.Dimension(320, 66));
        buttonPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        
        buttonPanel1.setMaximumSize(new java.awt.Dimension(32000, 32767));
        buttonPanel1.setMinimumSize(new java.awt.Dimension(320, 33));
        buttonPanel1.setPreferredSize(new java.awt.Dimension(320, 33));
        
        ADDCLASS_Button.setText("Add bookmark");
        buttonPanel1.add(ADDCLASS_Button);
        
        GENERATETRAININGSET_Button.setText("Generate training set");
        buttonPanel1.add(GENERATETRAININGSET_Button);
        
        buttonContainerPanel.add(buttonPanel1, java.awt.BorderLayout.CENTER);
        
        buttonPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        
        buttonPanel2.setMaximumSize(new java.awt.Dimension(32000, 32767));
        buttonPanel2.setMinimumSize(new java.awt.Dimension(320, 33));
        buttonPanel2.setPreferredSize(new java.awt.Dimension(320, 33));
        
        REMOVECLASS_Button.setText("Remove class");
        buttonPanel2.add(REMOVECLASS_Button);
        
        EDITCLASS_Button.setText("Editi class");
        buttonPanel2.add(EDITCLASS_Button);
        
        CLEARALL_Button.setText("Clear all");
        buttonPanel2.add(CLEARALL_Button);
        
        buttonContainerPanel.add(buttonPanel2, java.awt.BorderLayout.NORTH);
        
        tableContentPanel.add(buttonContainerPanel, java.awt.BorderLayout.SOUTH);
        
        editorPanel.add(tableContentPanel);
        
        rootContentPanel.add(editorPanel, java.awt.BorderLayout.NORTH);
        
        signalPanelContainer.setLayout(new java.awt.BorderLayout());
        
        framePositioningControlsPanel.setLayout(new java.awt.BorderLayout());
        
        bwd1000Button.setText("<<<");
        fdw_bwdButtonPanel.add(bwd1000Button);
        
        bwd100Button.setText("<<");
        fdw_bwdButtonPanel.add(bwd100Button);
        
        bwd10Button.setText("<");
        fdw_bwdButtonPanel.add(bwd10Button);
        
        fwd10Button.setText(">");
        fdw_bwdButtonPanel.add(fwd10Button);
        
        fwd100Button.setText(">>");
        fdw_bwdButtonPanel.add(fwd100Button);
        
        fwd1000Button.setText(">>>");
        fdw_bwdButtonPanel.add(fwd1000Button);
        
        framePositioningControlsPanel.add(fdw_bwdButtonPanel, java.awt.BorderLayout.NORTH);
        
        signalPanelContainer.add(framePositioningControlsPanel, java.awt.BorderLayout.NORTH);
        
        signalsContentPanel = new JPanel();
        signalsContentPanel.setLayout(new BoxLayout(signalsContentPanel, BoxLayout.Y_AXIS));
        signalPanel.setViewportView(signalsContentPanel);
        
        signalPanelContainer.add(signalPanel, java.awt.BorderLayout.CENTER);
        rootContentPanel.add(signalPanelContainer, java.awt.BorderLayout.CENTER);
        
        getContentPane().add(rootContentPanel, java.awt.BorderLayout.CENTER);
        setJMenuBar(applicationMenuBar);
        pack();
    }
    
    public RangesEditor getRangesEditor() {
        return rangesEditor;
    }
    
    public void setRangesEditor(RangesEditor aRangesEditor){
        rangesEditor = aRangesEditor;
    }
    
    public static void main(String args[]) {
        
        DataSource dsFile  = null;
        SignalDataSource aFileDataSource;
        SignalEditor aSignalEditor;
        aFileDataSource = new SignalDataSource();
        String file_separator = System.getProperty("file.separator");
        aFileDataSource.setMediaLocator(new MediaLocator("file:\\"+System.getProperty("user.dir")+file_separator+"examples"+file_separator+"basic"+file_separator+"audio_and_video"+file_separator+"testvideo.avi"));
        aFileDataSource.init();
        dsFile = aFileDataSource.getDataSource();
        aSignalEditor = new SignalEditor(dsFile);
        aSignalEditor.setVisible(true);
        aSignalEditor.start();
    }
}

class ClassesTableModel extends AbstractTableModel {
    
    private Vector rowVector = new Vector();
    private int index = 0;
    private int selectedRow = 0;
    private String[] columnNames = {"Begin",
    "End","Bookmark",
    "Sleep stage",
    "Use","CustomDataObject"};
    private Object[] longValues = {"", "", "","", Boolean.TRUE,""};
    private Object[] defaultRow = {"", "","Bookmark", "Stage 1", new Boolean(false), new Object()};
    
    
    public int getIndex(){
        return index;
    }
    
    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }
    
    public String[] getColumnNames() {
        return columnNames;
    }
    
    public void resetIndex(){
        index = 0;
    }
    
    public void increaseIndex(){
        index++;
        if(index>64000){
            index = 0;
        }
    }
    
    public Object[] getDefaultRow() {
        return defaultRow;
    }
    
    public void setDefaultRow(Object[] defaultRow) {
        this.defaultRow = defaultRow;
    }
    
    public void decreaseIndex(){
        index--;
        if(index<0){
            index = 0;
        }
    }
    
    public int getSelectedRow(){
        return selectedRow;
    }
    
    public void setSelectedRow(int aValue){
        selectedRow = aValue;
    }
    
    public void addRow(Object[] row){
        rowVector.addElement(row);
        fireTableRowsDeleted(rowVector.size(),rowVector.size());
    }
    
    public void removeRow(int index){
        rowVector.removeElementAt(index);
        fireTableRowsDeleted(index,index);
    }
    
    public void init(Object[] defaultRow){
        Object[] row = defaultRow;
        rowVector.addElement(row);
    }
    
    public int getColumnCount() {
        return columnNames.length;
    }
    
    public int getRowCount() {
        return rowVector.size();
    }
    
    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    public Object[] getRowAt(int row){
        return (Object[])rowVector.get(row);
    }
    
    public Object getValueAt(int row, int col) {
        Object[] tmp_row = (Object[])rowVector.get(row);
        return tmp_row[col];
    }
    
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
    
    public boolean isCellEditable(int row, int col) {
        if (col < 2) {
            return false;
        } else {
            return true;
        }
    }
    
    public void setValueAt(Object value, int row, int col) {
        Object[] tmp_row = (Object[])rowVector.get(row);
        tmp_row[col] = value;
        fireTableCellUpdated(row, col);
    }
    
    public Object[] getLongValues() {
        return longValues;
    }
    
    public Vector getRowVector() {
        return rowVector;
    }
    
    public void setIndex(int index) {
        this.index = index;
    }
    
    public void setLongValues(Object[] longValues) {
        this.longValues = longValues;
    }
    
    public void setRowVector(Vector rowVector) {
        this.rowVector = rowVector;
    }
}

final class ButtonIDs{
    
    private ButtonIDs(){
    }
    
    static String BWD10BUTTON = "0";
    static String BWD100BUTTON = "1";
    static String BWD1000BUTTON = "2";
    static String FWD10BUTTON = "3";
    static String FWD100BUTTON = "4";
    static String FWD1000BUTTON = "5";
    static String GENERATETRAININGSET = "6";
    static String CLEARALL = "7";
    static String ADDCLASS = "8";
    static String REMOVECLASS = "9";
    static String TOSELECTEDCLASS = "10";
    static String EDITCLASS = "11";
}

class TableEditingButtonListener implements ActionListener{
    private JTable table = null;
    private SignalEditor signalEditor = null;
    private double defaultWidth = 1.0/15.0;
    
    public TableEditingButtonListener(){
    }
    
    public TableEditingButtonListener(JTable aJTable, SignalEditor aSignalEditor){
        table = aJTable;
        signalEditor = aSignalEditor;
    }
    
    public void setEventSource(JTable aJTable){
        table = aJTable;;
    }
    
    public void setDefaultRangeWidth(double aValue){
        defaultWidth = aValue;
    }
    
    public double getDefaultRangeWidth(){
        return defaultWidth;
    }
    
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase(ButtonIDs.REMOVECLASS)){
            int selectedRow = table.getSelectedRow();
            if(selectedRow>=0){
                ((ClassesTableModel)table.getModel()).removeRow(selectedRow);
                ((ClassesTableModel)table.getModel()).decreaseIndex();
            }
        }
        
        
        if(e.getActionCommand().equalsIgnoreCase(ButtonIDs.EDITCLASS)){
            int selectedRow = table.getSelectedRow();
            if(selectedRow>=0){
                signalEditor.getRangesEditor().setClassesTableModel((ClassesTableModel)table.getModel());
                signalEditor.getRangesEditor().setVisible(true);
            }
        }
        
        if(e.getActionCommand().equalsIgnoreCase(ButtonIDs.CLEARALL)){
            int rows = table.getRowCount();
            for (int i=0; i < rows; i++) {
                ((ClassesTableModel)table.getModel()).removeRow(0);
                ((ClassesTableModel)table.getModel()).resetIndex();
            }
        }
        
        if(e.getActionCommand().equalsIgnoreCase(ButtonIDs.ADDCLASS)){
            Double begin = new Double(signalEditor.getProcessor().getMediaTime().getSeconds());
            Double end = new Double(signalEditor.getProcessor().getMediaTime().getSeconds());
            
            Object[] row = {begin,end+defaultWidth,"Bookmark " + ((ClassesTableModel)table.getModel()).getIndex(), (String)((ClassesTableModel)table.getModel()).getDefaultRow()[3], new Boolean(false), new CustomDataObject()};
            ((ClassesTableModel)table.getModel()).addRow(row);
            ((ClassesTableModel)table.getModel()).increaseIndex();
        }
        
        if(e.getActionCommand().equalsIgnoreCase(ButtonIDs.TOSELECTEDCLASS)){
            int selectedRow = table.getSelectedRow();
            ((ClassesTableModel)table.getModel()).setSelectedRow(selectedRow);
            if(selectedRow>=0){
                double timeStamp = ((Double)table.getModel().getValueAt(selectedRow,0)).doubleValue();
                signalEditor.getProcessor().setMediaTime(new Time(timeStamp));
            }
        }
    }
    
    public double getDefaultWidth() {
        return defaultWidth;
    }
    
    public SignalEditor getSignalEditor() {
        return signalEditor;
    }
    
    public JTable getTable() {
        return table;
    }
    
    public void setDefaultWidth(double defaultWidth) {
        this.defaultWidth = defaultWidth;
    }
    
    public void setSignalEditor(SignalEditor signalEditor) {
        this.signalEditor = signalEditor;
    }
    
    public void setTable(JTable table) {
        this.table = table;
    }
}

class CustomDataObject{
    private int x=0;
    private int y=0;
    private int width=0;
    private int heights=0;
    
    public CustomDataObject(){
    }
    
    public int getHeights() {
        return heights;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setHeights(int heights) {
        this.heights = heights;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
}

class ControlsButtonListener implements ActionListener{
    
    private SignalEditor signalEditor = null;
    
    public ControlsButtonListener(SignalEditor aSignalEditor){
        signalEditor = aSignalEditor;
    }
    
    public void setEventSource(SignalEditor aSignalEditor){
        signalEditor = aSignalEditor;
    }
    
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase(ButtonIDs.BWD10BUTTON)){
            signalEditor.getProcessor().setMediaTime(new Time(signalEditor.getProcessor().getMediaTime().getSeconds()-0.01));
        }
        if(e.getActionCommand().equalsIgnoreCase(ButtonIDs.BWD100BUTTON)){
            signalEditor.getProcessor().setMediaTime(new Time(signalEditor.getProcessor().getMediaTime().getSeconds()-0.1));
        }
        if(e.getActionCommand().equalsIgnoreCase(ButtonIDs.BWD1000BUTTON)){
            signalEditor.getProcessor().setMediaTime(new Time(signalEditor.getProcessor().getMediaTime().getSeconds()-1));
        }
        if(e.getActionCommand().equalsIgnoreCase(ButtonIDs.FWD10BUTTON)){
            signalEditor.getProcessor().setMediaTime(new Time(signalEditor.getProcessor().getMediaTime().getSeconds()+0.01));
        }
        if(e.getActionCommand().equalsIgnoreCase(ButtonIDs.FWD100BUTTON)){
            signalEditor.getProcessor().setMediaTime(new Time(signalEditor.getProcessor().getMediaTime().getSeconds()+0.1));
        }
        if(e.getActionCommand().equalsIgnoreCase(ButtonIDs.FWD1000BUTTON)){
            signalEditor.getProcessor().setMediaTime(new Time(signalEditor.getProcessor().getMediaTime().getSeconds()+1));
        }
    }
}

class ButtonRenderer extends JButton implements TableCellRenderer {
    
    public ButtonRenderer() {
        setOpaque(true);
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else{
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }
        setText( (value ==null) ? "" : value.toString() );
        return this;
    }
}

class ButtonEditor extends JButton implements TableCellEditor {
    public ButtonEditor() {
        super("selected");
    }
    
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        buttonPressed(table, row, column);
        return this;
    }
    
    public void cancelCellEditing() {
    }
    
    public boolean stopCellEditing() {
        return true;
    }
    
    public Object getCellEditorValue() {
        return null;
    }
    
    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }
    
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }
    
    public void addCellEditorListener(CellEditorListener l) {
    }
    
    public void removeCellEditorListener(CellEditorListener l) {
    }
    
    protected void fireCellEditing(ChangeEvent e){
    }
    
    private void buttonPressed(JTable table, int row, int column){
    }
}

class VideoFrameAccessor extends VideoDataAccessor{
    private CustomController controller;
    private SignalEditor signalEditor = null;
    
    public void setController(CustomController aController){
        controller = aController;
    }
    
    public void processInData(){
    }
    
    public SignalEditor getSignalEditor() {
        return signalEditor;
    }
    
    public void setSignalEditor(SignalEditor signalEditor) {
        this.signalEditor = signalEditor;
    }
}

class AudioFrameAccessor extends BasicAudioCodec{
    private boolean stopped = false;
    private CustomController controller;
    private SignalEditor signalEditor = null;
    private int secs = 0;
    
    
    public AudioFrameAccessor(){
        super();
    }
    
    public void setController(CustomController aController){
        controller = aController;
    }
    
    public void processInData(){
        System.out.println(secs);
        secs++;
        System.out.println(getInLength());
    }
    
    public SignalEditor getSignalEditor() {
        return signalEditor;
    }
    
    public void setSignalEditor(SignalEditor signalEditor) {
        this.signalEditor = signalEditor;
    }
}


class RangePanel extends PassiveRenderingPanel{
    private ClassesTableModel classesTableModel;
    private double rangeInSeconds = 0.0;
    private Processor processor;
    private int begin = 0;
    private int end = 0;
    private Double left;
    private Double right;
    private Color lightBlue = new Color(150,150,255,128);
    private Color darkBlue = new Color(50,50,255,128);
    private int hours_cursor = 0;
    private int minutes_cursor = 0;
    private float seconds_cursor = 0;
    private int hours = 0;
    private int minutes = 0;
    private float seconds = 0;
    
    public RangePanel(){
    }
    
    public void setSeconds_cursor(float seconds_cursor) {
        this.seconds_cursor = seconds_cursor;
    }
    
    public void setSeconds(float seconds) {
        this.seconds = seconds;
    }
    
    public void setRight(Double right) {
        this.right = right;
    }
    
    public void setMinutes_cursor(int minutes_cursor) {
        this.minutes_cursor = minutes_cursor;
    }
    
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
    
    public void setLightBlue(Color lightBlue) {
        this.lightBlue = lightBlue;
    }
    
    public void setLeft(Double left) {
        this.left = left;
    }
    
    public void setHours_cursor(int hours_cursor) {
        this.hours_cursor = hours_cursor;
    }
    
    public void setHours(int hours) {
        this.hours = hours;
    }
    
    public void setEnd(int end) {
        this.end = end;
    }
    
    public void setDarkBlue(Color darkBlue) {
        this.darkBlue = darkBlue;
    }
    
    public void setClassesTableModel(ClassesTableModel classesTableModel) {
        this.classesTableModel = classesTableModel;
    }
    
    public void setBegin(int begin) {
        this.begin = begin;
    }
    
    public float getSeconds_cursor() {
        return seconds_cursor;
    }
    
    public float getSeconds() {
        return seconds;
    }
    
    public Double getRight() {
        return right;
    }
    
    public int getMinutes_cursor() {
        return minutes_cursor;
    }
    
    public int getMinutes() {
        return minutes;
    }
    
    public Color getLightBlue() {
        return lightBlue;
    }
    
    public Double getLeft() {
        return left;
    }
    
    public int getHours_cursor() {
        return hours_cursor;
    }
    
    public int getHours() {
        return hours;
    }
    
    public int getEnd() {
        return end;
    }
    
    public Color getDarkBlue() {
        return darkBlue;
    }
    
    public ClassesTableModel getClassesTableModel() {
        return classesTableModel;
    }
    
    public int getBegin() {
        return begin;
    }
    
    private void rangesInSecodsToRangesInPixels(double beginInSeconds, double endInSeconds){
        
        double leftPanelPositionInSeconds = processor.getMediaTime().getSeconds();
        double rightPanelPositionInSeconds = processor.getMediaTime().getSeconds()+rangeInSeconds;
        begin = 0;
        end = 0;
        
        if(beginInSeconds<leftPanelPositionInSeconds
                && endInSeconds<rightPanelPositionInSeconds
                && endInSeconds>leftPanelPositionInSeconds){
            double viewableSeconds = endInSeconds-leftPanelPositionInSeconds;
            begin = 0;
            end = (int)Math.rint(viewableSeconds/(rangeInSeconds/getWidth()));
        }
        
        else if(beginInSeconds>leftPanelPositionInSeconds
                && beginInSeconds<rightPanelPositionInSeconds
                && endInSeconds>rightPanelPositionInSeconds){
            double viewableSeconds = rightPanelPositionInSeconds-beginInSeconds;
            begin = (int)Math.rint((rangeInSeconds-viewableSeconds)/(rangeInSeconds/getWidth()));
            end = getWidth();
        }
        
        else if(beginInSeconds>=leftPanelPositionInSeconds
                && beginInSeconds<=rightPanelPositionInSeconds
                && endInSeconds<=rightPanelPositionInSeconds
                && endInSeconds>=leftPanelPositionInSeconds){
            begin = (int)Math.rint((beginInSeconds-leftPanelPositionInSeconds)/(rangeInSeconds/getWidth()));
            end = (int)Math.rint((endInSeconds-leftPanelPositionInSeconds)/(rangeInSeconds/getWidth()));
        }
    }
    
    public void setRangeInSeconds(double aRangeInSeconds){
        rangeInSeconds = aRangeInSeconds;
    }
    
    public void setProcessor(Processor aProcessor){
        processor = aProcessor;
    }
    
    public Processor getProcessor(){
        return processor;
    }
    
    public double getRangeInSeconds(){
        return rangeInSeconds;
    }
    
    public void setModel(ClassesTableModel aClassesTableModel){
        classesTableModel = aClassesTableModel;
    }
    
    public void mousePressed(MouseEvent e) {
        left = new Double((getMousePositionX()*(rangeInSeconds/getWidth())+processor.getMediaTime().getSeconds()));
    }
    
    public void mouseReleased(MouseEvent e) {
        right = new Double((getMousePositionX()*(rangeInSeconds/getWidth())+processor.getMediaTime().getSeconds()));
        Object[] row = {left,right,(String)classesTableModel.getDefaultRow()[2] + classesTableModel.getIndex(), (String)classesTableModel.getDefaultRow()[3], new Boolean(false)};
        classesTableModel.addRow(row);
        classesTableModel.increaseIndex();
    }
    
    public void mouseClicked(MouseEvent e) {
    }
    
    public void render(int w, int h,Graphics2D g2d) {
        int index = 0;
        
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0,0,w,h);
        int selectedRow = 0;
        //draw ranges
        for (int i = 0; i < classesTableModel.getRowCount(); i++) {
            // compute begin and end coordinates before drawing the range
            rangesInSecodsToRangesInPixels(((Double)classesTableModel.getValueAt(i,0)).doubleValue(),((Double)classesTableModel.getValueAt(i,1)).doubleValue());
            selectedRow = classesTableModel.getSelectedRow();
            if(selectedRow!=i){
                g2d.setColor(lightBlue);
                g2d.fillRect(begin,0,end-begin,h);
            } else{
                g2d.setColor(darkBlue);
                g2d.fillRect(begin,0,end-begin,h);
                g2d.setColor(Color.WHITE);
                g2d.drawRect(begin,0,end-begin,h);
            }
            g2d.setColor(Color.WHITE);
            if(begin>0||end>getWidth()){
                g2d.drawString(classesTableModel.getValueAt(i,3).toString(),begin,h/2);
                g2d.drawString(classesTableModel.getValueAt(i,2).toString(),begin,h/2+15);
                g2d.drawString("Use for classification: " + classesTableModel.getValueAt(i,4).toString(),begin,h/2+30);
            }
        }
        
        g2d.setColor(Color.WHITE);
        System.out.println(getMousePositionY());
        g2d.drawLine(0,getMousePositionY(),w,getMousePositionY());
        g2d.drawLine(getMousePositionX(),0,getMousePositionX(),h);
        seconds_cursor = ((int)(((getMousePositionX()*(rangeInSeconds/w)+processor.getMediaTime().getSeconds())%60.0)*100));
        minutes_cursor = (int)((getMousePositionX()*(rangeInSeconds/w)+processor.getMediaTime().getSeconds())/60.0);
        hours_cursor = (int)(minutes_cursor/60);
        
        seconds = (int)((processor.getMediaTime().getSeconds()%60.0)*100);
        minutes = (int)(processor.getMediaTime().getSeconds()/60.0);
        hours = (int)(minutes/60);
        
        if((getMousePositionX()+50) < w){
            g2d.drawString(hours_cursor+":"+minutes_cursor+":"+seconds_cursor/100.0, getMousePositionX() +10, getMousePositionY() -10);
        }else{
            g2d.drawString(hours_cursor+":"+minutes_cursor+":"+seconds_cursor/100.0, getMousePositionX() +10 -70, getMousePositionY() -10);
        }
        g2d.setColor(lightBlue);
        g2d.fillRect(0,h-20,75,20);
        g2d.setColor(Color.WHITE);
        g2d.drawString(hours+":"+minutes+":"+seconds/100.0, 10, h-5);
        g2d.drawRect(0,h-20,75,19);
    }
}

class RangesEditor extends JDialog {
    private ClassesTableModel classesTableModel;
    private EditorButtonListener editorButtonListener;
    
    public void setTitlePanel(JPanel titlePanel) {
        this.titlePanel = titlePanel;
    }
    
    public void setTitleLabel(JLabel titleLabel) {
        this.titleLabel = titleLabel;
    }
    
    public void setTimeShiftPanel(JPanel timeShiftPanel) {
        this.timeShiftPanel = timeShiftPanel;
    }
    
    public void setTimeShiftLabelPanel(JPanel timeShiftLabelPanel) {
        this.timeShiftLabelPanel = timeShiftLabelPanel;
    }
    
    public void center(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int)dim.getWidth()/2-getWidth()/2,(int)dim.getHeight()/2-getHeight()/2);
    }
    
    public void setTimeShiftLabel(JLabel timeShiftLabel) {
        this.timeShiftLabel = timeShiftLabel;
    }
    
    public void setTimeShiftButtonPanel(JPanel timeShiftButtonPanel) {
        this.timeShiftButtonPanel = timeShiftButtonPanel;
    }
    
    public void setStartTimeLabelPanel(JPanel startTimeLabelPanel) {
        this.startTimeLabelPanel = startTimeLabelPanel;
    }
    
    public void setStartTimeLabel1(JLabel startTimeLabel1) {
        this.startTimeLabel1 = startTimeLabel1;
    }
    
    public void setStartTimeLabel(JLabel startTimeLabel) {
        this.startTimeLabel = startTimeLabel;
    }
    
    public void setStartTimeButtonlPanel(JPanel startTimeButtonlPanel) {
        this.startTimeButtonlPanel = startTimeButtonlPanel;
    }
    
    public void setShift10Button_start(JButton shift10Button_start) {
        this.shift10Button_start = shift10Button_start;
    }
    
    public void setShift10Button_end(JButton shift10Button_end) {
        this.shift10Button_end = shift10Button_end;
    }
    
    public void setShift100Button_start(JButton shift100Button_start) {
        this.shift100Button_start = shift100Button_start;
    }
    
    public void setShift100Button_end(JButton shift100Button_end) {
        this.shift100Button_end = shift100Button_end;
    }
    
    public void setShift1000Button_start(JButton shift1000Button_start) {
        this.shift1000Button_start = shift1000Button_start;
    }
    
    public void setShift1000Button_end(JButton shift1000Button_end) {
        this.shift1000Button_end = shift1000Button_end;
    }
    
    public void setRight10Button_start(JButton right10Button_start) {
        this.right10Button_start = right10Button_start;
    }
    
    public void setRight10Button_end(JButton right10Button_end) {
        this.right10Button_end = right10Button_end;
    }
    
    public void setRight100Button_start(JButton right100Button_start) {
        this.right100Button_start = right100Button_start;
    }
    
    public void setRight100Button_end(JButton right100Button_end) {
        this.right100Button_end = right100Button_end;
    }
    
    public void setRight1000Button_start(JButton right1000Button_start) {
        this.right1000Button_start = right1000Button_start;
    }
    
    public void setRight1000Button_end(JButton right1000Button_end) {
        this.right1000Button_end = right1000Button_end;
    }
    
    public void setOkButtonPanel(JPanel okButtonPanel) {
        this.okButtonPanel = okButtonPanel;
    }
    
    public void setOkButton(JButton okButton) {
        this.okButton = okButton;
    }
    
    public void setLeft10Button_start(JButton left10Button_start) {
        this.left10Button_start = left10Button_start;
    }
    
    public void setLeft10Button_end(JButton left10Button_end) {
        this.left10Button_end = left10Button_end;
    }
    
    public void setLeft100Button_start(JButton left100Button_start) {
        this.left100Button_start = left100Button_start;
    }
    
    public void setLeft100Button_end(JButton left100Button_end) {
        this.left100Button_end = left100Button_end;
    }
    
    public void setLeft1000Button_start(JButton left1000Button_start) {
        this.left1000Button_start = left1000Button_start;
    }
    
    public void setLeft1000Button_end(JButton left1000Button_end) {
        this.left1000Button_end = left1000Button_end;
    }
    
    public void setEndTimeLabelPanel(JPanel endTimeLabelPanel) {
        this.endTimeLabelPanel = endTimeLabelPanel;
    }
    
    public void setEndTimeComponentsPanel(JPanel endTimeComponentsPanel) {
        this.endTimeComponentsPanel = endTimeComponentsPanel;
    }
    
    public void setEndTimeButtonlPanel(JPanel endTimeButtonlPanel) {
        this.endTimeButtonlPanel = endTimeButtonlPanel;
    }
    
    public void setEditorPanel(JPanel editorPanel) {
        this.editorPanel = editorPanel;
    }
    
    public void setEditorButtonListener(RangesEditor.EditorButtonListener editorButtonListener) {
        this.editorButtonListener = editorButtonListener;
    }
    
    public JPanel getTitlePanel() {
        return titlePanel;
    }
    
    public JLabel getTitleLabel() {
        return titleLabel;
    }
    
    public JPanel getTimeShiftPanel() {
        return timeShiftPanel;
    }
    
    public JPanel getTimeShiftLabelPanel() {
        return timeShiftLabelPanel;
    }
    
    public JLabel getTimeShiftLabel() {
        return timeShiftLabel;
    }
    
    public JPanel getTimeShiftButtonPanel() {
        return timeShiftButtonPanel;
    }
    
    public JPanel getStartTimeLabelPanel() {
        return startTimeLabelPanel;
    }
    
    public JLabel getStartTimeLabel1() {
        return startTimeLabel1;
    }
    
    public JLabel getStartTimeLabel() {
        return startTimeLabel;
    }
    
    public JPanel getStartTimeButtonlPanel() {
        return startTimeButtonlPanel;
    }
    
    public JButton getShift10Button_start() {
        return shift10Button_start;
    }
    
    public JButton getShift10Button_end() {
        return shift10Button_end;
    }
    
    public JButton getShift100Button_start() {
        return shift100Button_start;
    }
    
    public JButton getShift100Button_end() {
        return shift100Button_end;
    }
    
    public JButton getShift1000Button_start() {
        return shift1000Button_start;
    }
    
    public JButton getShift1000Button_end() {
        return shift1000Button_end;
    }
    
    public JButton getRight10Button_start() {
        return right10Button_start;
    }
    
    public JButton getRight10Button_end() {
        return right10Button_end;
    }
    
    public JButton getRight100Button_start() {
        return right100Button_start;
    }
    
    public JButton getRight100Button_end() {
        return right100Button_end;
    }
    
    public JButton getRight1000Button_start() {
        return right1000Button_start;
    }
    
    public JButton getRight1000Button_end() {
        return right1000Button_end;
    }
    
    public JPanel getOkButtonPanel() {
        return okButtonPanel;
    }
    
    public JButton getOkButton() {
        return okButton;
    }
    
    public JButton getLeft10Button_start() {
        return left10Button_start;
    }
    
    public JButton getLeft10Button_end() {
        return left10Button_end;
    }
    
    public JButton getLeft100Button_start() {
        return left100Button_start;
    }
    
    public JButton getLeft100Button_end() {
        return left100Button_end;
    }
    
    public JButton getLeft1000Button_start() {
        return left1000Button_start;
    }
    
    public JButton getLeft1000Button_end() {
        return left1000Button_end;
    }
    
    public JPanel getEndTimeLabelPanel() {
        return endTimeLabelPanel;
    }
    
    public JPanel getEndTimeComponentsPanel() {
        return endTimeComponentsPanel;
    }
    
    public JPanel getEndTimeButtonlPanel() {
        return endTimeButtonlPanel;
    }
    
    public JPanel getEditorPanel() {
        return editorPanel;
    }
    
    public RangesEditor.EditorButtonListener getEditorButtonListener() {
        return editorButtonListener;
    }
    
    public ClassesTableModel getClassesTableModel() {
        return classesTableModel;
    }
    
    public RangesEditor(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        center();
    }
    
    public void setClassesTableModel(ClassesTableModel classesTableModel) {
        this.classesTableModel = classesTableModel;
    }
    
    public void setVisible(boolean b) {
        titleLabel.setText("You are modifying: "+classesTableModel.getValueAt(classesTableModel.getSelectedRow(),2));
        super.setVisible(b);
    }
    
    private void initComponents() {
        okButtonPanel = new JPanel();
        okButton = new JButton();
        titlePanel = new JPanel();
        titleLabel = new JLabel();
        editorPanel = new JPanel();
        startTimeLabelPanel = new JPanel();
        startTimeLabel = new JLabel();
        startTimeButtonlPanel = new JPanel();
        
        left10Button_start = new JButton();
        left100Button_start = new JButton();
        left1000Button_start = new JButton();
        right1000Button_start = new JButton();
        right100Button_start = new JButton();
        right10Button_start = new JButton();
        endTimeComponentsPanel = new JPanel();
        endTimeLabelPanel = new JPanel();
        startTimeLabel1 = new JLabel();
        endTimeButtonlPanel = new JPanel();
        left10Button_end = new JButton();
        left100Button_end = new JButton();
        left1000Button_end = new JButton();
        right1000Button_end = new JButton();
        right100Button_end = new JButton();
        right10Button_end = new JButton();
        timeShiftLabelPanel = new JPanel();
        timeShiftLabel = new JLabel();
        timeShiftPanel = new JPanel();
        timeShiftButtonPanel = new JPanel();
        shift1000Button_end = new JButton();
        shift1000Button_start = new JButton();
        shift100Button_end = new JButton();
        shift100Button_start = new JButton();
        shift10Button_end = new JButton();
        shift10Button_start = new JButton();
        
        
        editorButtonListener = new EditorButtonListener();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        
        timeShiftLabel.setText("shift range");
        timeShiftPanel.setLayout(new BorderLayout());
        timeShiftLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        timeShiftLabelPanel.add(timeShiftLabel);
        timeShiftPanel.add(timeShiftLabelPanel,BorderLayout.NORTH);
        timeShiftButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        timeShiftButtonPanel.add(shift10Button_start);
        timeShiftButtonPanel.add(shift100Button_start);
        timeShiftButtonPanel.add(shift1000Button_start);
        timeShiftButtonPanel.add(shift1000Button_end);
        timeShiftButtonPanel.add(shift100Button_end);
        timeShiftButtonPanel.add(shift10Button_end);
        timeShiftPanel.add(timeShiftButtonPanel,BorderLayout.CENTER);
        shift10Button_end.setText(">>>");
        shift100Button_end.setText(">>");
        shift1000Button_end.setText(">");
        shift10Button_start.setText("<<<");
        shift100Button_start.setText("<<");
        shift1000Button_start.setText("<");
        
        okButton.setText("OK");
        okButtonPanel.add(okButton);
        
        getContentPane().add(okButtonPanel, BorderLayout.SOUTH);
        
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        
        titleLabel.setText("You are modifying: ");
        titlePanel.add(titleLabel);
        
        getContentPane().add(titlePanel, BorderLayout.NORTH);
        
        editorPanel.setLayout(new java.awt.BorderLayout());
        
        startTimeLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        startTimeLabel.setText("starting time: ");
        startTimeLabelPanel.add(startTimeLabel);
        
        editorPanel.add(startTimeLabelPanel, BorderLayout.NORTH);
        
        left10Button_start.setText("<<<");
        left10Button_start.setMaximumSize(new Dimension(58, 23));
        left10Button_start.setMinimumSize(new Dimension(58, 23));
        left10Button_start.setPreferredSize(new Dimension(58, 23));
        startTimeButtonlPanel.add(left10Button_start);
        
        left100Button_start.setText("<<");
        startTimeButtonlPanel.add(left100Button_start);
        
        left1000Button_start.setText("<");
        startTimeButtonlPanel.add(left1000Button_start);
        
        right1000Button_start.setText(">");
        startTimeButtonlPanel.add(right1000Button_start);
        
        right100Button_start.setText(">>");
        startTimeButtonlPanel.add(right100Button_start);
        
        right10Button_start.setText(">>>");
        startTimeButtonlPanel.add(right10Button_start);
        
        editorPanel.add(startTimeButtonlPanel, BorderLayout.CENTER);
        
        endTimeComponentsPanel.setLayout(new BorderLayout());
        
        endTimeLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        startTimeLabel1.setText("ending time: ");
        endTimeLabelPanel.add(startTimeLabel1);
        
        endTimeComponentsPanel.add(endTimeLabelPanel, BorderLayout.NORTH);
        
        left10Button_end.setText("<<<");
        left10Button_end.setMaximumSize(new java.awt.Dimension(58, 23));
        left10Button_end.setMinimumSize(new java.awt.Dimension(58, 23));
        left10Button_end.setPreferredSize(new java.awt.Dimension(58, 23));
        endTimeButtonlPanel.add(left10Button_end);
        
        left100Button_end.setText("<<");
        endTimeButtonlPanel.add(left100Button_end);
        
        left1000Button_end.setText("<");
        endTimeButtonlPanel.add(left1000Button_end);
        
        right1000Button_end.setText(">");
        endTimeButtonlPanel.add(right1000Button_end);
        
        right100Button_end.setText(">>");
        endTimeButtonlPanel.add(right100Button_end);
        
        right10Button_end.setText(">>>");
        endTimeButtonlPanel.add(right10Button_end);
        
        endTimeComponentsPanel.add(endTimeButtonlPanel, BorderLayout.CENTER);
        endTimeComponentsPanel.add(timeShiftPanel,BorderLayout.SOUTH);
        editorPanel.add(endTimeComponentsPanel, BorderLayout.SOUTH);
        
        getContentPane().add(editorPanel, BorderLayout.CENTER);
        
        left10Button_start.setActionCommand(EditorButtonIDs.LEFT10BUTTON_START);
        left100Button_start.setActionCommand(EditorButtonIDs.LEFT100BUTTON_START);
        left1000Button_start.setActionCommand(EditorButtonIDs.LEFT1000BUTTON_START);
        left10Button_end.setActionCommand(EditorButtonIDs.LEFT10BUTTON_END);
        left100Button_end.setActionCommand(EditorButtonIDs.LEFT100BUTTON_END);
        left1000Button_end.setActionCommand(EditorButtonIDs.LEFT1000BUTTON_END);
        
        right10Button_start.setActionCommand(EditorButtonIDs.RIGHT10BUTTON_START);
        right100Button_start.setActionCommand(EditorButtonIDs.RIGHT100BUTTON_START);
        right1000Button_start.setActionCommand(EditorButtonIDs.RIGHT1000BUTTON_START);
        right10Button_end.setActionCommand(EditorButtonIDs.RIGHT10BUTTON_END);
        right100Button_end.setActionCommand(EditorButtonIDs.RIGHT100BUTTON_END);
        right1000Button_end.setActionCommand(EditorButtonIDs.RIGHT1000BUTTON_END);
        
        shift10Button_start.setActionCommand(EditorButtonIDs.SHIFT10BUTTON_START);
        shift100Button_start.setActionCommand(EditorButtonIDs.SHIFT100BUTTON_START);
        shift1000Button_start.setActionCommand(EditorButtonIDs.SHIFT1000BUTTON_START);
        shift10Button_end.setActionCommand(EditorButtonIDs.SHIFT10BUTTON_END);
        shift100Button_end.setActionCommand(EditorButtonIDs.SHIFT100BUTTON_END);
        shift1000Button_end.setActionCommand(EditorButtonIDs.SHIFT1000BUTTON_END);
        
        okButton.setActionCommand(EditorButtonIDs.OKBUTTON);
        
        left10Button_start.addActionListener(editorButtonListener);
        left100Button_start.addActionListener(editorButtonListener);
        left1000Button_start.addActionListener(editorButtonListener);
        left10Button_end.addActionListener(editorButtonListener);
        left100Button_end.addActionListener(editorButtonListener);
        left1000Button_end.addActionListener(editorButtonListener);
        
        right10Button_start.addActionListener(editorButtonListener);
        right100Button_start.addActionListener(editorButtonListener);
        right1000Button_start.addActionListener(editorButtonListener);
        right10Button_end.addActionListener(editorButtonListener);
        right100Button_end.addActionListener(editorButtonListener);
        right1000Button_end.addActionListener(editorButtonListener);
        
        shift10Button_start.addActionListener(editorButtonListener);
        shift100Button_start.addActionListener(editorButtonListener);
        shift1000Button_start.addActionListener(editorButtonListener);
        shift10Button_end.addActionListener(editorButtonListener);
        shift100Button_end.addActionListener(editorButtonListener);
        shift1000Button_end.addActionListener(editorButtonListener);
        
        okButton.addActionListener(editorButtonListener);
        
        pack();
    }
    
    private JPanel editorPanel;
    private JPanel endTimeButtonlPanel;
    private JPanel endTimeComponentsPanel;
    private JPanel endTimeLabelPanel;
    private JLabel titleLabel;
    private JButton left1000Button_end;
    private JButton left1000Button_start;
    private JButton left100Button_end;
    private JButton left100Button_start;
    private JButton left10Button_end;
    private JButton left10Button_start;
    private JButton okButton;
    private JPanel okButtonPanel;
    private JButton right1000Button_end;
    private JButton right1000Button_start;
    private JButton right100Button_end;
    private JButton right100Button_start;
    private JButton right10Button_end;
    private JButton right10Button_start;
    private JPanel startTimeButtonlPanel;
    private JLabel startTimeLabel;
    private JLabel startTimeLabel1;
    private JPanel startTimeLabelPanel;
    private JPanel titlePanel;
    private JPanel timeShiftPanel;
    private JLabel timeShiftLabel;
    private JPanel timeShiftLabelPanel;
    private JPanel timeShiftButtonPanel;
    private JButton shift1000Button_end;
    private JButton shift1000Button_start;
    private JButton shift100Button_end;
    private JButton shift100Button_start;
    private JButton shift10Button_end;
    private JButton shift10Button_start;
    
    
    
    final private class EditorButtonIDs{
        private EditorButtonIDs(){
        }
        
        final public static String LEFT10BUTTON_START = "0";
        final public static String LEFT100BUTTON_START = "1";
        final public static String LEFT1000BUTTON_START = "2";
        final public static String LEFT10BUTTON_END = "3";
        final public static String LEFT100BUTTON_END = "4";
        final public static String LEFT1000BUTTON_END = "5";
        
        final public static String RIGHT10BUTTON_START = "6";
        final public static String RIGHT100BUTTON_START = "7";
        final public static String RIGHT1000BUTTON_START = "8";
        final public static String RIGHT10BUTTON_END = "9";
        final public static String RIGHT100BUTTON_END = "10";
        final public static String RIGHT1000BUTTON_END = "11";
        
        final public static String SHIFT10BUTTON_START = "12";
        final public static String SHIFT100BUTTON_START = "13";
        final public static String SHIFT1000BUTTON_START = "14";
        final public static String SHIFT10BUTTON_END = "15";
        final public static String SHIFT100BUTTON_END = "16";
        final public static String SHIFT1000BUTTON_END = "17";
        
        final public static String OKBUTTON = "18";
    }
    
    private class EditorButtonListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e) {
            int selectedRow = classesTableModel.getSelectedRow();
            if (e.getActionCommand().equalsIgnoreCase(EditorButtonIDs.OKBUTTON)){
                setVisible(false);
            }
            if (e.getActionCommand().equalsIgnoreCase(EditorButtonIDs.LEFT10BUTTON_START)){
                Double currentValue = new Double((Double)classesTableModel.getValueAt(selectedRow,0));
                classesTableModel.setValueAt(new Double(currentValue.doubleValue()-1.0),selectedRow,0);
            }
            if (e.getActionCommand().equalsIgnoreCase(EditorButtonIDs.LEFT100BUTTON_START)){
                Double currentValue = new Double((Double)classesTableModel.getValueAt(selectedRow,0));
                classesTableModel.setValueAt(new Double(currentValue.doubleValue()-0.1),selectedRow,0);
            }
            if (e.getActionCommand().equalsIgnoreCase(EditorButtonIDs.LEFT1000BUTTON_START)){
                Double currentValue = new Double((Double)classesTableModel.getValueAt(selectedRow,0));
                classesTableModel.setValueAt(new Double(currentValue.doubleValue()-0.01),selectedRow,0);
            }
            if (e.getActionCommand().equalsIgnoreCase(EditorButtonIDs.RIGHT10BUTTON_START)){
                Double currentValue = new Double((Double)classesTableModel.getValueAt(selectedRow,0));
                classesTableModel.setValueAt(new Double(currentValue.doubleValue()+1.0),selectedRow,0);
            }
            if (e.getActionCommand().equalsIgnoreCase(EditorButtonIDs.RIGHT100BUTTON_START)){
                Double currentValue = new Double((Double)classesTableModel.getValueAt(selectedRow,0));
                classesTableModel.setValueAt(new Double(currentValue.doubleValue()+0.1),selectedRow,0);
            }
            if (e.getActionCommand().equalsIgnoreCase(EditorButtonIDs.RIGHT1000BUTTON_START)){
                Double currentValue = new Double((Double)classesTableModel.getValueAt(selectedRow,0));
                classesTableModel.setValueAt(new Double(currentValue.doubleValue()+0.01),selectedRow,0);
            }
            if (e.getActionCommand().equalsIgnoreCase(EditorButtonIDs.LEFT10BUTTON_END)){
                Double currentValue = new Double((Double)classesTableModel.getValueAt(selectedRow,1));
                classesTableModel.setValueAt(new Double(currentValue.doubleValue()-1.0),selectedRow,1);
            }
            if (e.getActionCommand().equalsIgnoreCase(EditorButtonIDs.LEFT100BUTTON_END)){
                Double currentValue = new Double((Double)classesTableModel.getValueAt(selectedRow,1));
                classesTableModel.setValueAt(new Double(currentValue.doubleValue()-0.1),selectedRow,1);
            }
            if (e.getActionCommand().equalsIgnoreCase(EditorButtonIDs.LEFT1000BUTTON_END)){
                Double currentValue = new Double((Double)classesTableModel.getValueAt(selectedRow,1));
                classesTableModel.setValueAt(new Double(currentValue.doubleValue()-0.01),selectedRow,1);
            }
            if (e.getActionCommand().equalsIgnoreCase(EditorButtonIDs.RIGHT10BUTTON_END)){
                Double currentValue = new Double((Double)classesTableModel.getValueAt(selectedRow,1));
                classesTableModel.setValueAt(new Double(currentValue.doubleValue()+1.0),selectedRow,1);
            }
            if (e.getActionCommand().equalsIgnoreCase(EditorButtonIDs.RIGHT100BUTTON_END)){
                Double currentValue = new Double((Double)classesTableModel.getValueAt(selectedRow,1));
                classesTableModel.setValueAt(new Double(currentValue.doubleValue()+0.1),selectedRow,1);
            }
            if (e.getActionCommand().equalsIgnoreCase(EditorButtonIDs.RIGHT1000BUTTON_END)){
                Double currentValue = new Double((Double)classesTableModel.getValueAt(selectedRow,1));
                classesTableModel.setValueAt(new Double(currentValue.doubleValue()+0.01),selectedRow,1);
            }
            if (e.getActionCommand().equalsIgnoreCase(EditorButtonIDs.SHIFT10BUTTON_START)){
                Double currentValue_left = new Double((Double)classesTableModel.getValueAt(selectedRow,0));
                Double currentValue_right = new Double((Double)classesTableModel.getValueAt(selectedRow,1));
                
                classesTableModel.setValueAt(new Double(currentValue_left.doubleValue()-1.0),selectedRow,0);
                classesTableModel.setValueAt(new Double(currentValue_right.doubleValue()-1.0),selectedRow,1);
            }
            if (e.getActionCommand().equalsIgnoreCase(EditorButtonIDs.SHIFT100BUTTON_START)){
                Double currentValue_left = new Double((Double)classesTableModel.getValueAt(selectedRow,0));
                Double currentValue_right = new Double((Double)classesTableModel.getValueAt(selectedRow,1));
                
                classesTableModel.setValueAt(new Double(currentValue_left.doubleValue()-0.1),selectedRow,0);
                classesTableModel.setValueAt(new Double(currentValue_right.doubleValue()-0.1),selectedRow,1);
            }
            if (e.getActionCommand().equalsIgnoreCase(EditorButtonIDs.SHIFT1000BUTTON_START)){
                Double currentValue_left = new Double((Double)classesTableModel.getValueAt(selectedRow,0));
                Double currentValue_right = new Double((Double)classesTableModel.getValueAt(selectedRow,1));
                
                classesTableModel.setValueAt(new Double(currentValue_left.doubleValue()-0.01),selectedRow,0);
                classesTableModel.setValueAt(new Double(currentValue_right.doubleValue()-0.01),selectedRow,1);
            }
            if (e.getActionCommand().equalsIgnoreCase(EditorButtonIDs.SHIFT10BUTTON_END)){
                Double currentValue_left = new Double((Double)classesTableModel.getValueAt(selectedRow,0));
                Double currentValue_right = new Double((Double)classesTableModel.getValueAt(selectedRow,1));
                
                classesTableModel.setValueAt(new Double(currentValue_left.doubleValue()+1.0),selectedRow,0);
                classesTableModel.setValueAt(new Double(currentValue_right.doubleValue()+1.0),selectedRow,1);
            }
            if (e.getActionCommand().equalsIgnoreCase(EditorButtonIDs.SHIFT100BUTTON_END)){
                Double currentValue_left = new Double((Double)classesTableModel.getValueAt(selectedRow,0));
                Double currentValue_right = new Double((Double)classesTableModel.getValueAt(selectedRow,1));
                
                classesTableModel.setValueAt(new Double(currentValue_left.doubleValue()+0.1),selectedRow,0);
                classesTableModel.setValueAt(new Double(currentValue_right.doubleValue()+0.1),selectedRow,1);
            }
            if (e.getActionCommand().equalsIgnoreCase(EditorButtonIDs.SHIFT1000BUTTON_END)){
                Double currentValue_left = new Double((Double)classesTableModel.getValueAt(selectedRow,0));
                Double currentValue_right = new Double((Double)classesTableModel.getValueAt(selectedRow,1));
                
                classesTableModel.setValueAt(new Double(currentValue_left.doubleValue()+0.01),selectedRow,0);
                classesTableModel.setValueAt(new Double(currentValue_right.doubleValue()+0.01),selectedRow,1);
            }
        }
    }
}