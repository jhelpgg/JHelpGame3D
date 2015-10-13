package jhelp.game3d.ui;

import jhelp.engine.Color4f;
import jhelp.engine.Font3D;
import jhelp.engine.Material;
import jhelp.engine.Node;
import jhelp.engine.Scene;
import jhelp.engine.geom.Box;
import jhelp.engine.util.PositionNode;
import jhelp.util.math.UtilMath;

/**
 * Edit text in 3D
 * 
 * @author JHelp
 */
public class EditText3D
{
   /** Current text node */
   private Node               currentNode;
   /** Cursor box */
   private final Box          cursor;
   /** Current cursor position */
   private int                cursorPosition;
   /** Text material */
   private Material           material;
   /** Edit text position */
   private final PositionNode positionNode;
   /** Scene where draw */
   private final Scene        scene;
   /** Current text */
   private String             text;
   /** Edit text visibility */
   private boolean            visible;

   /**
    * Create a new instance of EditText3D
    * 
    * @param scene
    *           Scene where draw
    * @param material
    *           Text material
    * @param text
    *           Initial text
    * @param visible
    *           Initial visibility
    */
   public EditText3D(final Scene scene, final Material material, final String text, final boolean visible)
   {
      if(scene == null)
      {
         throw new NullPointerException("scene musn't be null");
      }

      if(material == null)
      {
         throw new NullPointerException("material musn't be null");
      }

      if(text == null)
      {
         throw new NullPointerException("text musn't be null");
      }

      this.scene = scene;
      this.material = material;
      this.text = text;
      this.positionNode = new PositionNode();
      this.visible = visible;
      this.update();

      this.cursor = new Box();
      this.currentNode.computeTotalBox();
      this.cursor.setScale(0.1f, 1, 1);
      final Material materialCursor = Material.obtainMaterialOrCreate("EditText3DCursor");
      materialCursor.setColorDiffuse(Color4f.RED);
      this.cursor.setMaterial(materialCursor);
      this.currentNode.addChild(this.cursor);
      this.cursorPosition = -1;
      this.updateCursor();
   }

   /**
    * Update the edit text
    */
   private void update()
   {
      if(this.currentNode != null)
      {
         this.scene.remove(this.currentNode);
      }

      this.currentNode = Font3D.createString("Courier New", this.text, 0.001f, 1, 0.2f);
      this.currentNode.setVisible(this.visible);
      this.currentNode.setPosition(this.positionNode.x, this.positionNode.y, this.positionNode.z);
      this.currentNode.setScale(this.positionNode.scaleX, this.positionNode.scaleY, this.positionNode.scaleZ);
      this.currentNode.setAngleX(this.positionNode.angleX);
      this.currentNode.setAngleY(this.positionNode.angleY);
      this.currentNode.setAngleZ(this.positionNode.angleZ);
      this.currentNode.applyMaterialHierarchicaly(this.material);

      if(this.cursor != null)
      {
         this.currentNode.addChild(this.cursor);
      }

      this.scene.add(this.currentNode);
      this.scene.flush();
   }

   /**
    * Update the cursor
    */
   private void updateCursor()
   {
      this.cursor.setVisible((this.visible == true) && (this.cursorPosition >= 0));

      if(this.cursorPosition >= 0)
      {
         float x = 0;

         if((this.cursorPosition > 0) && (this.cursorPosition >= this.text.length()))
         {
            int position = this.text.length() - 1;
            float more = 0;

            while((position > 0) && (this.text.charAt(position) <= ' '))
            {
               position--;
               more += 0.2f;
            }

            x = this.currentNode.getChild(position).computeTotalBox().getMaxX() + more;
         }
         else if(this.cursorPosition < (this.currentNode.childCount() - 1))
         {
            x = this.currentNode.getChild(this.cursorPosition).getX();
         }
         else
         {
            x = this.currentNode.getX();
         }

         this.cursor.setPosition(x, this.cursor.getY(), this.cursor.getZ());
      }

      final Material materialCursor = Material.obtainMaterialOrCreate("EditText3DCursor");
      materialCursor.setColorDiffuse(Color4f.RED);
      this.cursor.setMaterial(materialCursor);
   }

   /**
    * Text material
    * 
    * @return Text material
    */
   public Material getMaterial()
   {
      return this.material;
   }

   /**
    * Edit text position
    * 
    * @return Edit text position
    */
   public PositionNode getPosition()
   {
      return new PositionNode(this.positionNode);
   }

   /**
    * Current text
    * 
    * @return Current text
    */
   public String getText()
   {
      return this.text;
   }

   /**
    * Edit text visibility
    * 
    * @return Edit text visibility
    */
   public boolean isVisible()
   {
      return this.visible;
   }

   /**
    * Defines edit text angle X
    * 
    * @param angle
    *           New angle X
    */
   public void setAngleX(final float angle)
   {
      this.positionNode.angleX = angle;
      this.currentNode.setAngleX(this.positionNode.angleX);
   }

   /**
    * Defines edit text angle Y
    * 
    * @param angle
    *           New angle Y
    */
   public void setAngleY(final float angle)
   {
      this.positionNode.angleY = angle;
      this.currentNode.setAngleY(this.positionNode.angleY);
   }

   /**
    * Defines edit text angle Z
    * 
    * @param angle
    *           New angle Z
    */
   public void setAngleZ(final float angle)
   {
      this.positionNode.angleZ = angle;
      this.currentNode.setAngleZ(this.positionNode.angleZ);
   }

   /**
    * Change cursor position.<br>
    * Use -1 for hide the cursor
    * 
    * @param cursorPosition
    *           New cursor position. Use -1 for hide the cursor
    */
   public void setCursorPosition(final int cursorPosition)
   {
      this.cursorPosition = UtilMath.limit(cursorPosition, -1, this.text.length());
      this.updateCursor();
   }

   /**
    * Change text material
    * 
    * @param material
    *           New text material
    */
   public void setMaterial(final Material material)
   {
      if(material == null)
      {
         throw new NullPointerException("material musn't be null");
      }

      this.material = material;
      this.currentNode.applyMaterialHierarchicaly(this.material);
      this.updateCursor();
   }

   /**
    * Change edit text location
    * 
    * @param x
    *           Position X
    * @param y
    *           Position Y
    * @param z
    *           Position Z
    */
   public void setPosition(final float x, final float y, final float z)
   {
      this.positionNode.x = x;
      this.positionNode.y = y;
      this.positionNode.z = z;
      this.currentNode.setPosition(this.positionNode.x, this.positionNode.y, this.positionNode.z);
   }

   /**
    * Change edit text scale
    * 
    * @param scale
    *           New scale
    */
   public void setScale(final float scale)
   {
      this.setScale(scale, scale, scale);
   }

   /**
    * Change edit text scale
    * 
    * @param scaleX
    *           X scale
    * @param scaleY
    *           Y scale
    * @param scaleZ
    *           Z scale
    */
   public void setScale(final float scaleX, final float scaleY, final float scaleZ)
   {
      this.positionNode.scaleX = scaleX;
      this.positionNode.scaleY = scaleY;
      this.positionNode.scaleZ = scaleZ;
      this.currentNode.setScale(this.positionNode.scaleX, this.positionNode.scaleY, this.positionNode.scaleZ);
   }

   /**
    * Change the text and put cursor at end of the new text
    * 
    * @param text
    *           New text
    */
   public void setText(final String text)
   {
      this.setText(text, text.length());
   }

   /**
    * Change text and cursor position
    * 
    * @param text
    *           New text
    * @param cursorPosition
    *           New cursor position. Use -1 for hide the cursor
    */
   public void setText(final String text, final int cursorPosition)
   {
      if(text == null)
      {
         throw new NullPointerException("text musn't be null");
      }

      if((this.text.equals(text) == true) && (this.cursorPosition == cursorPosition))
      {
         return;
      }

      this.text = text;
      this.update();

      this.cursorPosition = UtilMath.limit(cursorPosition, -1, this.text.length());
      this.updateCursor();
   }

   /**
    * Change edit text visibility
    * 
    * @param visible
    *           New edit text visibility
    */
   public void setVisible(final boolean visible)
   {
      this.visible = visible;
      this.currentNode.setVisibleHierarchy(visible);
      this.cursor.setVisible((visible == true) && (this.cursorPosition >= 0));
   }
}