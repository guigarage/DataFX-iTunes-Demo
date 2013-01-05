package com.guigarage.datafx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageTableCell extends TableCell<ITunesMedia, String> {

	public ImageTableCell() {
		final ImageView view = new ImageView();
		setGraphic(view);

		itemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observableValue,
					String oldValue, String newValue) {
				try {
					if (newValue != null) {
						view.setImage(new Image(newValue, true));
					} else {
						view.setImage(null);
					}
				} catch (Exception e) {
					view.setImage(null);
					e.printStackTrace();
				}
			}
		});
	}
}
