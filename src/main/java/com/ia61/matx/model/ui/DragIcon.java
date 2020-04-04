package com.ia61.matx.model.ui;

import com.ia61.matx.model.ui.DragIconType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.CubicCurve;

import java.io.IOException;

public class DragIcon extends AnchorPane{
	
	@FXML AnchorPane root_pane;
	@FXML Label unit_name;

	private DragIconType mType = null;
	private ModuleIcon moduleIcon;
	
	public DragIcon() {
		
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("/DragIcon.fxml")
				);
		
		fxmlLoader.setRoot(this); 
		fxmlLoader.setController(this);
		
		try { 
			fxmlLoader.load();
        
		} catch (IOException exception) {
		    throw new RuntimeException(exception);
		}
	}
	
	@FXML
	private void initialize() { }
	
	public void relocateToPoint (Point2D p) {

		//relocates the object to a point that has been converted to
		//scene coordinates
		Point2D localCoords = getParent().sceneToLocal(p);
		
		relocate ( 
				(int) (localCoords.getX() - (getBoundsInLocal().getWidth() / 2)),
				(int) (localCoords.getY() - (getBoundsInLocal().getHeight() / 2))
			);
	}

	public ModuleIcon getModuleIcon() {
		return moduleIcon;
	}

	public void setModuleIcon(ModuleIcon moduleIcon) {
		this.moduleIcon = moduleIcon;
		this.unit_name.setText(moduleIcon.getName());
	}

	public DragIconType getType() { return mType; }

	public void setType(DragIconType type) {

		mType = type;
		
		getStyleClass().clear();
		getStyleClass().add("dragicon");
		
		//added because the cubic curve will persist into other icons
		if (this.getChildren().size() > 0)
			getChildren().clear();
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
}