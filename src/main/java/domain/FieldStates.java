package domain;

import java.util.ArrayList;

import data_source.MyFieldInsnNode;

public class FieldStates {
	
	public ArrayList<MyFieldInsnNode> fieldStoring;
	public ArrayList<MyFieldInsnNode> fieldLoading;
	
	public FieldStates() {
		fieldStoring = new ArrayList<>();
		fieldLoading = new ArrayList<>();
	}
	
	public void empty() {
		fieldStoring.removeAll(fieldStoring);
		fieldLoading.removeAll(fieldLoading);
	}

}
