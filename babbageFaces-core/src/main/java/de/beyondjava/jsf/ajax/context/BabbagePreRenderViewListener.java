package de.beyondjava.jsf.ajax.context;

import java.util.List;

import javax.faces.component.*;
import javax.faces.event.*;

/**
 * This class add a unique marker to each JSF DOM node which doesn't change even
 * if the JSF elements visibility (rendered attribute) changes. The marker
 * enables the DifferentialResponseWriter to recognize nodes that can be
 * inserted into the HTML tree.
 * 
 * @author Stephan Rauh http://www.beyondjava.net
 * 
 */
/**
 * (C) 2013-2014 Stephan Rauh http://www.beyondjava.net
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
public class BabbagePreRenderViewListener implements SystemEventListener {

   /**
    * @param root
    * @param parentID
    */
   private void annotateChildren(UIComponent root, String parentID) {
      int index = 0;
      List<UIComponent> children = root.getChildren();
      for (UIComponent c : children) {
         final String id = parentID + String.valueOf(index++);
         c.getAttributes().put("babbageid", id);
         if (c.getId().startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
            c.setId(id);
         }
         else if (!c.getId().contains("BabbageID_")) {
            if (c.getPassThroughAttributes().containsKey("BabbageID")) {
               c.getPassThroughAttributes().remove("BabbageID");
            }
            c.getPassThroughAttributes().put("BabbageID", id);
         }
         annotateChildren(c, id + "_");
      }
   }

   @Override
   public boolean isListenerForSource(Object source) {
      return true;
   }

   @Override
   public void processEvent(SystemEvent event) throws AbortProcessingException {
      UIViewRoot root = (UIViewRoot) event.getSource();

      String parentID = "BabbageID_";
      // annotateChildren(root, parentID);
   }

}