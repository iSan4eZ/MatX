package com.ia61.matx.model.ui;

import com.ia61.matx.module.Module;
import com.ia61.matx.util.Point2dSerial;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.*;

import static com.ia61.matx.constants.UIConstants.DRAGGABLE_NODE_HEADER_HEIGHT;

public class DraggableNode extends AnchorPane {

	@FXML private AnchorPane root_pane;
	@FXML private AnchorPane left_link_handle;
	@FXML private AnchorPane right_link_handle;
	@FXML private VBox inputs;
	@FXML private VBox outputs;
	@FXML private Label title_bar;
	@FXML private Label close_button;
	@FXML private HBox node_content;

	private EventHandler <MouseEvent> mLinkHandleDragDetected;
	private EventHandler <DragEvent> mLinkHandleDragDropped;
	private EventHandler <DragEvent> mContextLinkDragOver;
	private EventHandler <DragEvent> mContextLinkDragDropped;

	private EventHandler <DragEvent> mContextDragOver;
	private EventHandler <DragEvent> mContextDragDropped;

	private DragIconType mType = null;

	private Point2D mDragOffset = new Point2D (0.0, 0.0);

	private final DraggableNode self;

	private NodeLink mDragLink = null;
	private AnchorPane right_pane = null;
	private Module module;
	private List<String> inputsList = new ArrayList<>();
	private List<String> outputsList = new ArrayList<>();
	private ModuleIcon moduleIcon;

	private final List <String> mLinkIds = new ArrayList <String> ();
	private List <String> takenInputs = new ArrayList<>();

	public DraggableNode(ModuleIcon moduleIcon) {
		setModule(moduleIcon.getModule());
		setModuleIcon(moduleIcon);

		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("/DraggableNode.fxml")
		);

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);


		self = this;

		try {
			fxmlLoader.load();

		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		//provide a universally unique identifier for this object
		setId(UUID.randomUUID().toString());

	}

	@FXML
	private void initialize() {

		buildNodeDragHandlers();
		buildLinkDragHandlers();

		setTitle_bar(moduleIcon.getName());

		mDragLink = new NodeLink();
		mDragLink.setVisible(false);

		parentProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable,
					Object oldValue, Object newValue) {
				right_pane = (AnchorPane) getParent();

			}

		});

		Circle c =new Circle();
		c.setRadius(5.0f);
		this.getChildren().add(c);

		setType(DragIconType.grey);


		setLinkPanesAmount(inputs,
                getModule().getInputCount()
        );
		setLinkPanesAmount(outputs,
                getModule().getOutputCount()
        );
		inputs.setVisible(true);
		outputs.setVisible(true);
	}

	private void addLinkPanel(VBox linkPanes, double paneHeight) {
		AnchorPane anchorPane = new AnchorPane();
		anchorPane.setId(UUID.randomUUID().toString());
		anchorPane.setMinHeight(paneHeight);
		anchorPane.setMinWidth(linkPanes.getPrefWidth());
		if (linkPanes.getId().contains("inputs")) {
			anchorPane.getStyleClass().add("left-link-handle");
			inputsList.add(anchorPane.getId());
		} else if (linkPanes.getId().contains("outputs")) {
			outputsList.add(anchorPane.getId());
			anchorPane.getStyleClass().add("right-link-handle");
		}

        Circle c =new Circle();
        c.setRadius(5.0f);
        anchorPane.getChildren().add(c);


		anchorPane.setOnDragDetected(mLinkHandleDragDetected);
		anchorPane.setOnDragDropped(mLinkHandleDragDropped);

		anchorPane.setVisible(true);
		linkPanes.getChildren().add(anchorPane);
	}

	private void setLinkPanesAmount(VBox linkPanes, int portsAmount) {
		double paneHeight = linkPanes.getPrefHeight();
		if(portsAmount > 0) {
            paneHeight = paneHeight / portsAmount;
			for (int i = 0; i < portsAmount; i++) {
				addLinkPanel(linkPanes, paneHeight);
			}
		} else if (portsAmount == -1) {
			paneHeight = linkPanes.getPrefHeight();
			addLinkPanel(linkPanes, paneHeight);
		} else {
			System.out.println("panes will not be added");
		}
	}

	public void registerLink(String linkId) {
		mLinkIds.add(linkId);
	}

	public void removeLink(String linkId) {
		inputsList.remove(linkId);
		outputsList.remove(linkId);
		mLinkIds.remove(linkId);
	}

	public List<String> getLinkIds() {
		return mLinkIds;
	}

	public Module getModule() {
		return module;
	}

	public void relocateToPoint (Point2D p) {

		//relocates the object to a point that has been converted to
		//scene coordinates
		Point2D localCoords = getParent().sceneToLocal(p);

		relocate (
				(int) (localCoords.getX() - mDragOffset.getX()),
				(int) (localCoords.getY() - mDragOffset.getY())
		);
	}

	public String getTitle_bar() {
		return title_bar.getText();
	}

	public void setTitle_bar(String title_bar) {
		if(this.title_bar == null) {
			this.title_bar = new Label();
		}
		this.title_bar.setText(title_bar);
	}

	public DragIconType getType () { return mType; }

	public void setType (DragIconType type) {

		mType = type;

		getStyleClass().clear();
		getStyleClass().add("dragicon");

		switch (mType) {

			case blue:
				getStyleClass().add("icon-blue");
				break;

			case red:
				getStyleClass().add("icon-red");
				break;

			case green:
				getStyleClass().add("icon-green");
				break;

			case grey:
				getStyleClass().add("icon-grey");
				break;

			case purple:
				getStyleClass().add("icon-purple");
				break;

			case yellow:
				getStyleClass().add("icon-yellow");
				break;

			case black:
				getStyleClass().add("icon-black");
				break;

			default:
				break;
		}
	}

	public void buildNodeDragHandlers() {

		mContextDragOver = new EventHandler <DragEvent>() {

			//dragover to handle node dragging in the right pane view
			@Override
			public void handle(DragEvent event) {

				event.acceptTransferModes(TransferMode.ANY);
				relocateToPoint(new Point2dSerial( event.getSceneX(), event.getSceneY()));

				event.consume();
			}
		};

		//dragdrop for node dragging
		mContextDragDropped = new EventHandler <DragEvent> () {

			@Override
			public void handle(DragEvent event) {

				getParent().setOnDragOver(null);
				getParent().setOnDragDropped(null);

				event.setDropCompleted(true);

				event.consume();
			}
		};

		//close button click
		close_button.setOnMouseClicked( new EventHandler <MouseEvent> () {

			@Override
			public void handle(MouseEvent event) {
				AnchorPane parent  = (AnchorPane) self.getParent();
				parent.getChildren().remove(self);

				//iterate each link id connected to this node
				//find it's corresponding component in the right-hand
				//AnchorPane and delete it.

				//Note:  other nodes connected to these links are not being
				//notified that the link has been removed.
				for (ListIterator <String> iterId = mLinkIds.listIterator();
						iterId.hasNext();) {

					String id = iterId.next();

					for (ListIterator <Node> iterNode = parent.getChildren().listIterator();
							iterNode.hasNext();) {

						Node node = iterNode.next();

						if (node.getId() == null)
							continue;

						if (node.getId().equals(id))
							iterNode.remove();
					}

					iterId.remove();
				}
			}

		});

		//node content double click (show popup window)
		node_content.setOnMouseClicked(event -> {
			if (event.getButton().equals(MouseButton.PRIMARY)) {
				if (event.getClickCount() == 2) {
					new PropertyWindow(module.getPopupFields());
				}
			}
		});

		//drag detection for node dragging
		title_bar.setOnDragDetected ( new EventHandler <MouseEvent> () {

			@Override
			public void handle(MouseEvent event) {

				getParent().setOnDragOver(null);
				getParent().setOnDragDropped(null);

				getParent().setOnDragOver (mContextDragOver);
				getParent().setOnDragDropped (mContextDragDropped);

				//begin drag ops
				mDragOffset = new Point2D(event.getX(), event.getY());

				relocateToPoint(
						new Point2D(event.getSceneX(), event.getSceneY())
				);

				ClipboardContent content = new ClipboardContent();
				DragContainer container = new DragContainer();

//					container.addData ("type", mType.toString());
				content.put(DragContainer.AddNode, container);

				startDragAndDrop (TransferMode.ANY).setContent(content);

				event.consume();
			}

		});
	}

	private void buildLinkDragHandlers() {

		mLinkHandleDragDetected = new EventHandler <MouseEvent> () {

			@Override
			public void handle(MouseEvent event) {

				getParent().setOnDragOver(null);
				getParent().setOnDragDropped(null);

				getParent().setOnDragOver(mContextLinkDragOver);
				getParent().setOnDragDropped(mContextLinkDragDropped);

				//Set up user-draggable link
				right_pane.getChildren().add(0,mDragLink);

				mDragLink.setVisible(false);

				double x = getLayoutX();
				double y = getLayoutY();

				AnchorPane sourcePane = (AnchorPane) event.getSource();

				// set left link
				for (Node child : inputs.getChildren()) {
					if (child.getId().equals(sourcePane.getId())) {
						x += +5;
						y += child.getLayoutY() + DRAGGABLE_NODE_HEADER_HEIGHT + (outputs.getPrefHeight() / outputs.getChildren().size()) / 2;
					}
				}
				if(x == getLayoutX() && y == getLayoutY()) {
					// set right link
					for (Node child : outputs.getChildren()) {
						if (child.getId().equals(sourcePane.getId())) {
							x += getPrefWidth() - 5;
							y += child.getLayoutY() + DRAGGABLE_NODE_HEADER_HEIGHT + (outputs.getPrefHeight() / outputs.getChildren().size()) / 2;
						}
					}
				}
				Point2D p = new Point2D(x, y);

				mDragLink.setStart(p);

				//Drag content code
				ClipboardContent content = new ClipboardContent();
				DragContainer container = new DragContainer();

				//pass the UUID of the source node for later lookup
				container.addData("source", sourcePane.getId());

				content.put(DragContainer.AddLink, container);

				startDragAndDrop (TransferMode.ANY).setContent(content);

				event.consume();
			}
		};

		mLinkHandleDragDropped = new EventHandler <DragEvent> () {

			@Override
			public void handle(DragEvent event) {

				getParent().setOnDragOver(null);
				getParent().setOnDragDropped(null);

				//get the drag data.  If it's null, abort.
				//This isn't the drag event we're looking for.
				DragContainer container =
						(DragContainer) event.getDragboard().getContent(DragContainer.AddLink);

				if (container == null)
					return;

				//hide the draggable NodeLink and remove it from the right-hand AnchorPane's children
				mDragLink.setVisible(false);
				right_pane.getChildren().remove(0);

				AnchorPane targetPane = (AnchorPane) event.getSource();

				ClipboardContent content = new ClipboardContent();

				//pass the UUID of the target node for later lookup
				container.addData("target", targetPane.getId());

				content.put(DragContainer.AddLink, container);

				event.getDragboard().setContent(content);
				event.setDropCompleted(true);
				event.consume();
			}
		};

		mContextLinkDragOver = new EventHandler <DragEvent> () {

			@Override
			public void handle(DragEvent event) {
				event.acceptTransferModes(TransferMode.ANY);

				//Relocate end of user-draggable link
				if (!mDragLink.isVisible())
					mDragLink.setVisible(true);

				mDragLink.setEnd(new Point2D(event.getX(), event.getY()));

				event.consume();

			}
		};

		//drop event for link creation
		mContextLinkDragDropped = new EventHandler <DragEvent> () {

			@Override
			public void handle(DragEvent event) {
				System.out.println("context link drag dropped");

				getParent().setOnDragOver(null);
				getParent().setOnDragDropped(null);

				//hide the draggable NodeLink and remove it from the right-hand AnchorPane's children
				mDragLink.setVisible(false);
				right_pane.getChildren().remove(0);

				event.setDropCompleted(true);
				event.consume();
			}

		};

	}

	public VBox getInputs() {
		return inputs;
	}

	public VBox getOutputs() {
		return outputs;
	}

	private void setModule(Module module) {
		this.module = module;
	}

	public int getInputIndexNumber(String paneID) {
		return findIndexNumber(inputsList, paneID);
	}

	public int getOutputIndexNumber(String paneID) {
		return findIndexNumber(outputsList, paneID);
	}

	private int findIndexNumber(List <String> elements, String paneID) {
		int index = 0;
		for (String s : elements) {
			if(s.equals(paneID)) {
				return index;
			}
			index += 1;
		}
		return -1;
	}

	public ModuleIcon getModuleIcon() {
		return moduleIcon;
	}

	public void setModuleIcon(ModuleIcon moduleIcon) {
		this.moduleIcon = moduleIcon;
	}

	public void addTakenInput(String inputID) {
		takenInputs.add(inputID);
	}

	public List<String> getTakenInputs() {
		return takenInputs;
	}
}