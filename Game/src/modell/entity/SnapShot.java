package modell.entity;

import java.awt.Image;
import java.util.List;

import view.IFPaintable;

public class SnapShot implements IFPaintable{

	/*	The getPolygonData method returns information about the polygon state.
	 * 	This is just a representation of the polygons state, it is only used by clients.
	 * 	Image is immutable, so its safe to share this.
	 * 
	 *  So why a snapshot class?
	 *  To avoid concurrent modification of the entity list.
	 *  When adding new entities, the entity list may be in use by the client responsible of painting.
	 *  Therefore I use an List of Snapshots that is only updated when the client calls for 
	 *  getPaintableSnapShot(), which means that list can be updated securely.
	 */
	
	private Entity entity;

	public SnapShot(Entity e){
		entity = e;
	}
	
	@Override
	public List<Integer> getPolygonData() {
		return entity.getPolygonData();
	}

	@Override
	public Image getImage() {
		return entity.getImage();
	}

	@Override
	public int getImageX() {
		return entity.getImageX();
	}

	@Override
	public int getImageY() {
		return entity.getImageY();
	}

	@Override
	public int getLayerId() {
		return 0;
	}
	
}
