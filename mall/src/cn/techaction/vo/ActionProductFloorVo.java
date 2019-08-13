package cn.techaction.vo;

import java.util.List;

import cn.techaction.pojo.ActionProduct;

public class ActionProductFloorVo {
	private List<ActionProduct> firstFloor;//一楼数据
	private List<ActionProduct> secondFloor;//二楼数据
	private List<ActionProduct> thirdFloor;//三楼数据
	private List<ActionProduct> forthFloor;//四楼数据
	public List<ActionProduct> getFirstFloor() {
		return firstFloor;
	}
	public void setFirstFloor(List<ActionProduct> firstFloor) {
		this.firstFloor = firstFloor;
	}
	public List<ActionProduct> getSecondFloor() {
		return secondFloor;
	}
	public void setSecondFloor(List<ActionProduct> secondFloor) {
		this.secondFloor = secondFloor;
	}
	public List<ActionProduct> getThirdFloor() {
		return thirdFloor;
	}
	public void setThirdFloor(List<ActionProduct> thirdFloor) {
		this.thirdFloor = thirdFloor;
	}
	public List<ActionProduct> getForthFloor() {
		return forthFloor;
	}
	public void setForthFloor(List<ActionProduct> forthFloor) {
		this.forthFloor = forthFloor;
	}

}
