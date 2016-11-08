package de.flapdoodle.ui.sample;

import com.google.common.collect.ImmutableList;
import com.sun.javafx.scene.control.behavior.BehaviorBase;

public class MyBehavior extends BehaviorBase<MyControl> {

	public MyBehavior(MyControl control) {
		super(control, ImmutableList.of());
	}
}