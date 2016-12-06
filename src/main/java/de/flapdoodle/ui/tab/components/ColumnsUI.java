package de.flapdoodle.ui.tab.components;

import de.flapdoodle.ui.components.BoxBuilder;
import de.flapdoodle.ui.components.Sizes;
import de.flapdoodle.ui.components.clone.CellUtils;
import de.flapdoodle.ui.tab.data.Columns;
import de.flapdoodle.ui.tab.data.EntityId;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class ColumnsUI extends Control {

	private Button dragButton;

	private ColumnsUI(EntityId<Columns> columnsId) {
		setSkin(new ColumnsUISkin(this));
		//setPrefHeight(100);
		//setPrefWidth(50);
		
		Paint paint=new Color(0.9,0.9,0.9,0.9);
		CornerRadii rad=new CornerRadii(5.0);
		Insets insets=new Insets(-4, -4, -4, -4);
		setBackground(new Background(new BackgroundFill(paint, rad, insets)));
		
		
		Label dragMeHere = new Label("[d]");
		
		VBox content = BoxBuilder.vbox()
			.add(new Label(columnsId.uuid().toString()))
			.add(BoxBuilder.hbox()
					.add(Priority.ALWAYS,Sizes.asBigAsPossible(new Label("???")))
					.add(Priority.SOMETIMES,dragMeHere)
					.build())
			.add(Priority.ALWAYS, dummyTable())
			.build();
		
		
		
//		Button btn = new Button();
//		btn.setText(columnsId.uuid().toString());
//		//getChildren().add(btn);
//		Button dragBtn = new Button();
//		dragBtn.setText("[d]");
//		
//		VBox layout = new VBox(2);
//		layout.getChildren().addAll(btn, dragBtn);
//		getChildren().add(layout);
//		
//		this.dragButton=dragBtn;
//		this.dragButton.getProperties().put(Dragging.DRAG_ME, true);
		
		getChildren().add(content);
		
		Dragging.moveNodeBy(this, dragMeHere);
	}
	
	private TableView<Integer> dummyTable() {
		TableView<Integer> tableView = new TableView<>();
		tableView.setEditable(true);
		
		TableColumn<Integer, String> scolumn=new TableColumn<>("Text");
		scolumn.setEditable(true);
		scolumn.setCellFactory(textFieldF(new DefaultStringConverter()));
		scolumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<String>(""+param.getValue()));
		scolumn.setOnEditCommit((CellEditEvent<Integer, String> e) -> {
			System.out.println(" -> "+e.getTablePosition()+" = "+e.getOldValue()+" -> "+e.getNewValue());
		});
		
		TableColumn<Integer, Integer> icolumn=new TableColumn<>("Zahl");
		icolumn.setCellFactory(textFieldF(new IntegerStringConverter()));
		//icolumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		icolumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<Integer>(param.getValue()));
		icolumn.setEditable(true);
		
		tableView.setItems(FXCollections.observableArrayList(1,2,3,4));
		
		tableView.getColumns().addAll(scolumn, icolumn);
		return tableView;
	}

	public static Node of(EntityId<Columns> columnsId) {
		ColumnsUI columnsUI = new ColumnsUI(columnsId);
		return columnsUI; //Dragging.makeDraggable(columnsUI.dragButton, columnsUI); 
	}
	
	private static class ColumnsUISkin extends SkinBase<ColumnsUI> {

		protected ColumnsUISkin(ColumnsUI control) {
			super(control);
		}
	}

	
	public static <S,T> Callback<TableColumn<S,T>, TableCell<S,T>> textFieldF(StringConverter<T> converter) {
		Callback<TableColumn<S,T>, TableCell<S,T>> cellFactory =
         new Callback<TableColumn<S,T>, TableCell<S,T>>() {
             public TableCell<S,T> call(TableColumn<S,T> p) {
                return new EditingCell<S,T>(converter);
             }
         };
         return cellFactory;
	}
	
    static class EditingCell<S, T> extends TableCell<S, T> {
    	 
    	public EditingCell(StringConverter<T> converter) {
    		this.getStyleClass().add("text-field-table-cell");
    		setConverter(converter);
		}
    	
        /***************************************************************************
         *                                                                         *
         * Fields                                                                  *
         *                                                                         *
         **************************************************************************/

        private TextField textField;



        /***************************************************************************
         *                                                                         *
         * Properties                                                              *
         *                                                                         *
         **************************************************************************/

        // --- converter
        private ObjectProperty<StringConverter<T>> converter =
                new SimpleObjectProperty<StringConverter<T>>(this, "converter");

        /**
         * The {@link StringConverter} property.
         */
        public final ObjectProperty<StringConverter<T>> converterProperty() {
            return converter;
        }

        /**
         * Sets the {@link StringConverter} to be used in this cell.
         */
        public final void setConverter(StringConverter<T> value) {
            converterProperty().set(value);
        }

        /**
         * Returns the {@link StringConverter} used in this cell.
         */
        public final StringConverter<T> getConverter() {
            return converterProperty().get();
        }



        /***************************************************************************
         *                                                                         *
         * Public API                                                              *
         *                                                                         *
         **************************************************************************/

        /** {@inheritDoc} */
        @Override public void startEdit() {
            if (! isEditable()
                    || ! getTableView().isEditable()
                    || ! getTableColumn().isEditable()) {
                return;
            }
            super.startEdit();

            if (isEditing()) {
                if (textField == null) {
                    textField = CellUtils.createTextField(this, getConverter());
                    textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
                      @Override
                      public void changed(ObservableValue<? extends Boolean> arg0, 
                          Boolean arg1, Boolean arg2) {
                              if (!arg2) {
                                  commitEdit(getConverter().fromString(textField.getText()));
                              }
                      }
                  });
                }

                CellUtils.startEdit(this, getConverter(), null, null, textField);
            }
        }

        /** {@inheritDoc} */
        @Override public void cancelEdit() {
            super.cancelEdit();
            CellUtils.cancelEdit(this, getConverter(), null);
        }

        /** {@inheritDoc} */
        @Override public void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            CellUtils.updateItem(this, getConverter(), null, null, textField);
        }

    	
//        private TextField textField;
// 
//        public EditingCell() {
//        }
// 
//        @Override
//        public void startEdit() {
//            if (!isEmpty()) {
//                super.startEdit();
//                createTextField();
//                setText(null);
//                setGraphic(textField);
//                textField.selectAll();
//            }
//        }
// 
//        @Override
//        public void cancelEdit() {
//            super.cancelEdit();
// 
//            setText((String) getItem());
//            setGraphic(null);
//        }
// 
//        @Override
//        public void updateItem(String item, boolean empty) {
//            super.updateItem(item, empty);
// 
//            if (empty) {
//                setText(null);
//                setGraphic(null);
//            } else {
//                if (isEditing()) {
//                    if (textField != null) {
//                        textField.setText(getString());
//                    }
//                    setText(null);
//                    setGraphic(textField);
//                } else {
//                    setText(getString());
//                    setGraphic(null);
//                }
//            }
//        }
// 
//        private void createTextField() {
//            textField = new TextField(getString());
//            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
//            textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
//                @Override
//                public void changed(ObservableValue<? extends Boolean> arg0, 
//                    Boolean arg1, Boolean arg2) {
//                        if (!arg2) {
//                            commitEdit(textField.getText());
//                        }
//                }
//            });
//        }
// 
//        private String getString() {
//            return getItem() == null ? "" : getItem().toString();
//        }
    }
}
