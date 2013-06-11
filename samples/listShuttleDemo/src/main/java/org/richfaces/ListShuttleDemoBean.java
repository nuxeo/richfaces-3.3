/**
 * 
 */
package org.richfaces;

import java.util.Collection;
import java.util.Random;

import javax.faces.context.FacesContext;

/**
 * @author Nick Belaevski
 *         mailto:nbelaevski@exadel.com
 *         created 16.11.2007
 *
 */
public class ListShuttleDemoBean {

	private Collection sourceSelection;
	private Collection targetSelection;
	
	private String[] zebraItems = new String[] {"0", "1", "2", "3", "4"};
	
	private ListShuttleOptionItem[] source;
	
	private ListShuttleOptionItem[] target;
	
	private Integer[] numbers = new Integer[10];
	
	private boolean moveControlsVisible = true;
	private boolean fastMoveControlsVisible = true;
	
	private boolean orderControlsVisible = true;
	private boolean fastOrderControlsVisible = true;

	private boolean switchByClick = false;
	private boolean switchByDblClick = true;
	
	
	public ListShuttleDemoBean() {
		super();
		
		source = new ListShuttleOptionItem[20];
		for (int i = 0; i < 20; i++) {
			source[i] = new ListShuttleOptionItem("Source Item " + i, new Random().nextInt(40));
		}

		target = new ListShuttleOptionItem[20];
		for (int i = 0; i < 20; i++) {
			target[i] = new ListShuttleOptionItem("Target Item " + i, new Random().nextInt(40));
		}
		
		/*for (int i = 0; i < 2000; i++) {
			numbers[i] = new Random().nextInt(256);
		}*/
	}
	
	public ListShuttleOptionItem[] getSource() {
		return source;
	}

	private static String arrayToString(Object[] objects) {
		StringBuffer result = new StringBuffer();
		if (objects == null) {
			result.append("null");
		} else {
			int iMax = objects.length - 1;
			if (iMax == -1) {
				result.append("[]");
			} else {
				result.append('[');
				for (int i = 0; i <= iMax; i++) {
					result.append(objects[i]);
					if (i != iMax) {
						result.append(", ");
					}
				}
				result.append(']');
			}
		}
		
		return result.toString();
	}
	
	public void setSource(ListShuttleOptionItem[] source) {
		System.out.print("ListShuttleDemoBean.setSource() ");
		System.out.println(arrayToString(source));
		
		this.source = source;
	}
	
	public ListShuttleOptionItem[] getTarget() {
		return target;
	}
	
	public void setTarget(ListShuttleOptionItem[] target) {
		System.out.println("ListShuttleDemoBean.setTarget() " + arrayToString(target));
		this.target = target;
	}
	
	public void startOver() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("listShuttleDemoBean");
	}

	public boolean isMoveControlsVisible() {
		return moveControlsVisible;
	}

	public void setMoveControlsVisible(boolean moveControlsVisible) {
		this.moveControlsVisible = moveControlsVisible;
	}

	public boolean isFastMoveControlsVisible() {
		return fastMoveControlsVisible;
	}

	public void setFastMoveControlsVisible(boolean fastMoveControlsVisible) {
		this.fastMoveControlsVisible = fastMoveControlsVisible;
	}

	public boolean isOrderControlsVisible() {
		return orderControlsVisible;
	}

	public void setOrderControlsVisible(boolean orderControlsVisible) {
		this.orderControlsVisible = orderControlsVisible;
	}

	public boolean isFastOrderControlsVisible() {
		return fastOrderControlsVisible;
	}

	public void setFastOrderControlsVisible(boolean fastOrderControlsVisible) {
		this.fastOrderControlsVisible = fastOrderControlsVisible;
	}

	public Collection getSourceSelection() {
		return sourceSelection;
	}

	public void setSourceSelection(Collection sourceSelection) {
		System.out.println("ListShuttleDemoBean.setSourceSelection() " + sourceSelection);
		this.sourceSelection = sourceSelection;
	}

	public Collection getTargetSelection() {
		return targetSelection;
	}

	public void setTargetSelection(Collection targetSelection) {
		System.out.println("ListShuttleDemoBean.setTargetSelection() " + targetSelection);
		this.targetSelection = targetSelection;
	}
	
	public Integer[] getNumbers() {
		return numbers;
	}
	
	public void setNumbers(Integer[] numbers) {
		for (int i = 0; i < numbers.length; i++) {
			System.out.print(numbers[i].getClass().getSimpleName() + " ");
		}
		System.out.println();
		
		this.numbers = numbers;
	}

	public String[] getZebraItems() {
		return zebraItems;
	}

	public void setZebraItems(String[] zebraItems) {
		this.zebraItems = zebraItems;
	}
	
	public boolean isSwitchByClick() {
		return switchByClick;
	}

	public void setSwitchByClick(boolean switchByClick) {
		this.switchByClick = switchByClick;
	}

	public boolean isSwitchByDblClick() {
		return switchByDblClick;
	}

	public void setSwitchByDblClick(boolean switchByDblClick) {
		this.switchByDblClick = switchByDblClick;
	}

}
