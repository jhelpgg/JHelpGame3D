package jhelp.game3d.samples.miinimal;

import java.awt.HeadlessException;
import java.awt.event.KeyEvent;

import jhelp.engine.Animation;
import jhelp.engine.JHelpSceneRenderer;
import jhelp.engine.Node;
import jhelp.engine.Scene;
import jhelp.engine.anim.AnimationPause;
import jhelp.engine.anim.MultiAnimation;
import jhelp.engine.gui.ActionKey;
import jhelp.engine.util.PositionNode;
import jhelp.game3d.models.BodyPart;
import jhelp.game3d.models.CharacterHumanFemale;
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
   /** Last manipulated mode change time */
   private long             changeTime;
   /** Indicates if user agreed to exit */
   private boolean          confirmedExit;
   /** Indicates if manipulate the character */
   private boolean          manipulateNode  = true;

   /** Manipulated node sample */
   private Node             node;
   /** Mirror camera position */
   private PositionNode     position;

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
            if(this.manipulateNode)
            {
               this.node.translate(0, 1, 0);
            }
            else
            {
               this.position.y += 1;
            }
         break;
         case DOWN:
            if(this.manipulateNode)
            {
               this.node.translate(0, -1, 0);
            }
            else
            {
               this.position.y -= 1;
            }
         break;
         case LEFT:
            if(this.manipulateNode)
            {
               this.node.translate(-1, 0, 0);
            }
            else
            {
               this.position.x -= 1;
            }
         break;
         case RIGHT:
            if(this.manipulateNode)
            {
               this.node.translate(1, 0, 0);
            }
            else
            {
               this.position.x += 1;
            }
         break;
         case FORWARD:
            if(this.manipulateNode)
            {
               this.node.translate(0, 0, 1);
            }
            else
            {
               this.position.z += 1;
            }
         break;
         case BACKWARD:
            if(this.manipulateNode)
            {
               this.node.translate(0, 0, -1);
            }
            else
            {
               this.position.z -= 1;
            }
         break;
         case ROTATE_LEFT:
            this.node.rotateAngleY(1f);
         break;
         case ROTATE_RIGHT:
            this.node.rotateAngleY(-1f);
         break;
         case EXIT:
            this.closeGame();
         break;
         case CANCEL:
            if((System.currentTimeMillis() - this.changeTime) > 2048)
            {
               this.changeTime = System.currentTimeMillis();
               this.manipulateNode = !this.manipulateNode;
            }
         break;
         default:
         break;
      }

      // if(this.manipulateNode)
      // {
      // Debug.println(DebugLevel.INFORMATION, new PositionNode(this.node));
      // }
      // else
      // {
      // Debug.println(DebugLevel.INFORMATION, this.position);
      // }
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
    * Called when game initialized <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param sceneRenderer
    *           Scene renderer
    * @param scene
    *           Scene where draw
    * @see jhelp.engine.gui.Game3DFrame#initializeGame(jhelp.engine.JHelpSceneRenderer, jhelp.engine.Scene)
    */
   @Override
   protected void initializeGame(final JHelpSceneRenderer sceneRenderer, final Scene scene)
   {
      this.confirmedExit = false;
      final CharacterHumanFemale female = new CharacterHumanFemale();
      female.setDressScale(0.5f);
      final MultiAnimation animation = new MultiAnimation(true);
      animation.addAnimation(female.obtainRunAnimation());
      animation.addAnimation(female.obtainRunAnimation());
      animation.addAnimation(female.obtainRunAnimation());
      animation.addAnimation(female.obtainPutAtStartPositionAnimation(25));
      animation.addAnimation(female.obtainWalkFastAnimation());
      animation.addAnimation(female.obtainWalkFastAnimation());
      animation.addAnimation(female.obtainWalkFastAnimation());
      animation.addAnimation(female.obtainPutAtStartPositionAnimation(25));
      animation.addAnimation(female.obtainWalkNormalAnimation());
      animation.addAnimation(female.obtainWalkNormalAnimation());
      animation.addAnimation(female.obtainWalkNormalAnimation());
      animation.addAnimation(female.obtainPutAtStartPositionAnimation(25));
      animation.addAnimation(female.obtainWalkSlowAnimation());
      animation.addAnimation(female.obtainWalkSlowAnimation());
      animation.addAnimation(female.obtainWalkSlowAnimation());
      animation.addAnimation(female.obtainPutAtStartPositionAnimation(25));

      final int nb = female.numberOfSpecificAnimation();
      Animation specific;

      for(int i = 0; i < nb; i++)
      {
         specific = female.obtainSpecificAnimation(i);
         animation.addAnimation(specific);
         animation.addAnimation(specific);
         animation.addAnimation(specific);
         animation.addAnimation(female.obtainPutAtStartPositionAnimation(25));
      }

      animation.addAnimation(new AnimationPause(25));

      this.node = female.getCharacterNode();
      female.setTexture(BodyPart.DRESS, ResourcesGame3D.obtainTexture("emerald_bk.jpg"));
      female.obtainMaterial(BodyPart.DRESS).setTextureSpheric(ResourcesGame3D.obtainTexture("emerald_bk.jpg"));
      female.obtainMaterial(BodyPart.DRESS).setSphericRate(0.5f);
      this.node.setPosition(-4.0f, -63.0f, -529.0f);
      scene.add(this.node);

      // Add mirror for example, for see performance impact, remove comments bellow (and do necessary imports)
      // Plane planeMirior = new Plane(false, true);
      // planeMirior.setPosition(0.0f, -133.0f, -617.0f);
      // planeMirior.setAngleX(-90);
      // planeMirior.scale(1000);
      // scene.add(planeMirior);
      // this.position = new PositionNode(1.0f, 568.0f, -858.0f, -90, 0, 0);
      // Miror miror = new Miror(planeMirior, this.position);
      // miror.backgroundRed = 0.5f;
      // miror.backgroundGreen = 0.6f;
      // miror.backgroundBlue = 0.8f;
      // miror.backgroundAlpha = 0.5f;
      // sceneRenderer.addMiror(miror);

      // If your computer not on its knee, you can try remove comments bellow also, to see effect two mirrors see each other
      // planeMirior = new Plane(false, true);
      // planeMirior.setPosition(0.0f, 133.0f, -617.0f);
      // planeMirior.setAngleX(90);
      // planeMirior.scale(1000);
      // scene.add(planeMirior);
      // this.position = new PositionNode(1.0f, -568.0f, -858.0f, 90, 0, 0);
      // miror = new Miror(planeMirior, this.position);
      // miror.backgroundRed = 0.5f;
      // miror.backgroundGreen = 0.6f;
      // miror.backgroundBlue = 0.8f;
      // miror.backgroundAlpha = 0.5f;
      // sceneRenderer.addMiror(miror);

      sceneRenderer.playAnimation(animation);
      // sceneRenderer.setShowFPS(true);
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