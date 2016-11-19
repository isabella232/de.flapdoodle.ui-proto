/**
 * Copyright (C) 2016
 *   Michael Mosmann <michael@mosmann.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.ui;

import de.flapdoodle.ui.sample.MyControl;
import de.flapdoodle.ui.tab.DocumentUI;
import de.flapdoodle.ui.tab.Overview;
import de.flapdoodle.ui.tab.data.Document;
import de.flapdoodle.ui.tab.threading.SynchronizedDelegate;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        SynchronizedDelegate<Document> doc = SynchronizedDelegate.of(new Document());
        
        MyControl myControl = new MyControl();
        Overview overview = new Overview();
        DocumentUI docView = new DocumentUI(doc);
        
        //StackPane root = new StackPane();
//        GridPane root = new GridPane();
//        GridPane.setConstraints(btn, 0,0);
//        GridPane.setConstraints(myControl, 1,0);
//        GridPane.setConstraints(overview, 0,1,3,3);
//        GridPane.setConstraints(docView, 0,2,3,3);
//		root.getChildren().addAll(btn, myControl, overview, docView);
		
		BorderPane root=new BorderPane();
		root.setTop(myControl);
		//root.setRight(overview);
		root.setCenter(docView);
		
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
