package com.guigarage.datafx;

import java.util.Locale;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.HBoxBuilder;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.javafxdata.control.cell.MoneyTableCell;
import org.javafxdata.datasources.Format;
import org.javafxdata.datasources.provider.ObjectDataSource;
import org.javafxdata.datasources.provider.ObjectDataSourceBuilder;
import org.javafxdata.datasources.reader.RestRequest;
import org.javafxdata.datasources.reader.RestRequestBuilder;

public class ITunesSearch extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		final ObservableList<ITunesMedia> items = FXCollections
				.<ITunesMedia> observableArrayList();

		TableView<ITunesMedia> tableView = new TableView<>(items);

		TableColumn<ITunesMedia, String> nameColumn = new TableColumn<>("Name");
		nameColumn
				.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ITunesMedia, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(
							CellDataFeatures<ITunesMedia, String> cellDataFeatures) {
						return new SimpleStringProperty(cellDataFeatures
								.getValue().getTrackName());
					}
				});

		TableColumn<ITunesMedia, Number> priceColumn = new TableColumn<>(
				"Price");
		priceColumn
				.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ITunesMedia, Number>, ObservableValue<Number>>() {

					@Override
					public ObservableValue<Number> call(
							CellDataFeatures<ITunesMedia, Number> cellDataFeatures) {
						return new SimpleDoubleProperty(cellDataFeatures
								.getValue().getTrackPrice());
					}
				});
		priceColumn
				.setCellFactory(new Callback<TableColumn<ITunesMedia, Number>, TableCell<ITunesMedia, Number>>() {

					@Override
					public TableCell<ITunesMedia, Number> call(
							TableColumn<ITunesMedia, Number> column) {
						return new MoneyTableCell<>(Locale.US);
					}
				});

		TableColumn<ITunesMedia, String> imageColumn = new TableColumn<>(
				"Image");
		imageColumn
				.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ITunesMedia, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(
							CellDataFeatures<ITunesMedia, String> cellDataFeatures) {
						return new SimpleStringProperty(cellDataFeatures
								.getValue().getArtworkUrl100());
					}
				});
		imageColumn
				.setCellFactory(new Callback<TableColumn<ITunesMedia, String>, TableCell<ITunesMedia, String>>() {

					@Override
					public TableCell<ITunesMedia, String> call(
							TableColumn<ITunesMedia, String> arg0) {
						return new ImageTableCell();
					}
				});

		tableView.getColumns().addAll(nameColumn, imageColumn, priceColumn);

		final TextField searchField = new TextField();
		Button searchButton = new Button("search");
		searchButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				RestRequest request = new RestRequestBuilder(
						"http://itunes.apple.com").path("search")
						.format(Format.JSON)
						.queryParam("term", searchField.getText())
						.queryParam("media", "music").build();
				ObjectDataSource<ITunesMedia> dataSource = ObjectDataSourceBuilder
						.<ITunesMedia> create().dataSourceReader(request)
						.resultList(items).itemTag("results")
						.itemClass(ITunesMedia.class).build();
				dataSource.retrieve();
			}
		});

		Scene myScene = new Scene(BorderPaneBuilder
				.create()
				.top(HBoxBuilder.create().children(searchField, searchButton)
						.build()).center(tableView).build());
		stage.setScene(myScene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}
