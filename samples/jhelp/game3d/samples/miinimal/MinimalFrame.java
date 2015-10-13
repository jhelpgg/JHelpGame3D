package jhelp.game3d.samples.miinimal;

import java.awt.HeadlessException;
import java.awt.event.KeyEvent;

import jhelp.engine.Material;
import jhelp.engine.Node;
import jhelp.engine.NodeWithMaterial;
import jhelp.engine.Scene;
import jhelp.engine.geom.Ribbon3D;
import jhelp.engine.gui.ActionKey;
import jhelp.game3d.resources.ResourcesGame3D;
import jhelp.game3d.ui.JHelpGame3DFrame;
import jhelp.game3d.ui.OptionPaneButtons;
import jhelp.util.debug.Debug;
import jhelp.util.debug.DebugLevel;

/**
 * Sample frame.<br>
 * Hit "action" (space or joystick button down) and type "hello" and return for have a character dialog sample
 * 
 * @author JHelp
 */
public class MinimalFrame
      extends JHelpGame3DFrame
{
   /** Dialog for user confirm he want exit or not */
   private static final int DIALOG_EXIT_NOW = 0;
   /** Indicates if user agreed to exit */
   private boolean          confirmedExit;
   /** Manipulated node sample */
   private final Node       node;

   /**
    * Create a new instance of MinimalFrame
    * 
    * @throws HeadlessException
    *            If OS can't create the frame
    */
   public MinimalFrame()
         throws HeadlessException
   {
      super("Minimal");

      this.confirmedExit = false;
      this.node = new Ribbon3D(1);
      final Material material = Material.obtainMaterialOrCreate("Main");
      material.setTextureDiffuse(ResourcesGame3D.obtainTexture("emerald_bk.jpg"));
      ((NodeWithMaterial) this.node).setMaterial(material);
      this.node.setPosition(0, 0, -7);
      final Scene scene = this.getSceneRenderer().getScene();
      scene.add(this.node);
      scene.flush();
   }

   /**
    * Called when user hit an action key or hit the joystick (conerted on action) <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param actionKey
    *           Action happen
    * @see jhelp.game3d.ui.JHelpGame3DFrame#actionKeyInternal(jhelp.engine.gui.ActionKey)
    */
   @Override
   protected void actionKeyInternal(final ActionKey actionKey)
   {
      switch(actionKey)
      {
         case ACTION:
            this.startEditText();
         break;
         case MENU:
            this.startCaptureNextKey();
         break;
         case UP:
            this.node.translate(0, 0.1f, 0);
         break;
         case DOWN:
            this.node.translate(0, -0.1f, 0);
         break;
         case LEFT:
            this.node.translate(-0.1f, 0, 0);
         break;
         case RIGHT:
            this.node.translate(0.1f, 0, 0);
         break;
         case FORWARD:
            this.node.translate(0, 0, 0.1f);
         break;
         case BACKWARD:
            this.node.translate(0, 0, -0.1f);
         break;
         case ROTATE_LEFT:
            this.node.rotateAngleX(1f);
         break;
         case ROTATE_RIGHT:
            this.node.rotateAngleX(-1f);
         break;
         default:
         break;
      }
   }

   /**
    * Called when frame want to close. <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @return {@code true} if it allowed to exit now OR {@code false} if not allowed
    * @see jhelp.engine.gui.Game3DFrame#canExitNow()
    */
   @Override
   protected boolean canExitNow()
   {
      if(this.confirmedExit == false)
      {
         this.showOptionPane(ResourcesGame3D.obtainText(ResourcesGame3D.TEXT_EXIT_NOW), OptionPaneButtons.YES_NO, MinimalFrame.DIALOG_EXIT_NOW);
         return false;
      }

      return true;
   }

   /**
    * Called when a character dialog is closed <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @see jhelp.game3d.ui.JHelpGame3DFrame#characterDialogClose()
    */
   @Override
   protected void characterDialogClose()
   {
      // {@todo} TODO Implements dialogClose
      Debug.printTodo("Implements dialogClose");
   }

   /**
    * Called when a key is captured <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param keyCode
    *           Key code captured
    * @see jhelp.engine.gui.Game3DFrame#keyCodeCaptured(int)
    */
   @Override
   protected void keyCodeCaptured(final int keyCode)
   {
      Debug.println(DebugLevel.INFORMATION, keyCode, " : ", KeyEvent.getKeyText(keyCode));
      this.backToGame();
   }

   /**
    * Called when user make a choice in option pane <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param optionPaneButtons
    *           Option pane button choose by the user
    * @param optionPaneID
    *           Option pane ID that user answered
    * @see jhelp.game3d.ui.JHelpGame3DFrame#optionPaneButtonClicked(jhelp.game3d.ui.OptionPaneButtons, int)
    */
   @Override
   protected void optionPaneButtonClicked(final OptionPaneButtons optionPaneButtons, final int optionPaneID)
   {
      switch(optionPaneID)
      {
         case DIALOG_EXIT_NOW:
            if(optionPaneButtons == OptionPaneButtons.YES)
            {
               this.confirmedExit = true;
               this.closeGame();
            }
         break;
      }
   }

   /**
    * Called when user type enter in text edition <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param text
    *           Text typed
    * @see jhelp.game3d.ui.JHelpGame3DFrame#textTyped(java.lang.String)
    */
   @Override
   protected void textTyped(final String text)
   {
      if("hello".equalsIgnoreCase(text.trim()) == true)
      {
         this.showCharacterDialog(
               "Hello guy !\nSo you notice me, I thought I was invisible.\nMay be you feel my presence, not see me.\nIn that case I'm impressed by your talent.\nIt means that you may be the one I searched so long time.\nDo you mind if I test your potential ?\nI ask the question just to be polite, but I don't plan to let you the choice.\nSo here we go !",
               "aerith.png", "Angel");
      }
   }
}