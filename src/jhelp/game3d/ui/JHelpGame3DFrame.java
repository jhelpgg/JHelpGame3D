package jhelp.game3d.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.TreeSet;

import jhelp.engine.JHelpSceneRenderer;
import jhelp.engine.Material;
import jhelp.engine.Texture;
import jhelp.engine.TextureAlphabetText;
import jhelp.engine.event.Object2DListener;
import jhelp.engine.gui.ActionKey;
import jhelp.engine.gui.Game3DFrame;
import jhelp.engine.gui.KeyboardMode;
import jhelp.engine.twoD.GUI2D;
import jhelp.engine.twoD.Object2D;
import jhelp.game3d.resources.ResourcesGame3D;
import jhelp.gui.ResourcesGUI;
import jhelp.linux.joystick.Axis;
import jhelp.linux.joystick.Button;
import jhelp.linux.joystick.ButtonType;
import jhelp.linux.joystick.Joystick;
import jhelp.linux.joystick.JoystickListener;
import jhelp.linux.joystick.JoystickManager;
import jhelp.util.gui.JHelpImage;
import jhelp.util.gui.JHelpTextAlign;
import jhelp.util.gui.UtilGUI;
import jhelp.util.gui.alphabet.Alphabet;
import jhelp.util.gui.alphabet.AlphabetBlue16x16;
import jhelp.util.gui.alphabet.AlphabetGraphitti;
import jhelp.util.gui.alphabet.AlphabetGreen8x16;
import jhelp.util.gui.alphabet.AlphabetOrange16x16;
import jhelp.util.gui.alphabet.AlphabetText;
import jhelp.util.thread.ThreadManager;
import jhelp.util.thread.ThreadedVerySimpleTask;

/**
 * Generic frame for 3D game.<br>
 * It embed tools for have option pane, create character dialogs, input some text, joystick management, and more in future
 * 
 * @author JHelp
 */
public abstract class JHelpGame3DFrame
      extends Game3DFrame
{
   /**
    * Manage joystick event, and convert them to virtual action, so it makes no difference if user use joystick or keyboard
    * 
    * @author JHelp
    */
   private class JoystickEventManager
         extends ThreadedVerySimpleTask
         implements JoystickListener, Object2DListener
   {
      /** Current active axis */
      private final TreeSet<Axis>       axis;
      /** Current down buttons */
      private final TreeSet<ButtonType> buttons;
      /** Indicates if we are in game mode or user interface */
      private boolean                   gameMode;

      /**
       * Create a new instance of JoystickEventManager
       */
      JoystickEventManager()
      {
         this.gameMode = true;
         this.axis = new TreeSet<Axis>();
         this.buttons = new TreeSet<ButtonType>();
         ThreadManager.THREAD_MANAGER.repeatThread(this, null, 1024, 32);
      }

      /**
       * Consume all joystick events
       */
      private void consumeAll()
      {
         synchronized(this.axis)
         {
            this.axis.clear();
         }

         synchronized(this.buttons)
         {
            this.buttons.clear();
         }
      }

      /**
       * Do task for report frequently joystick status <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @see jhelp.util.thread.ThreadedVerySimpleTask#doVerySimpleAction()
       */
      @Override
      protected void doVerySimpleAction()
      {
         synchronized(this.axis)
         {
            for(final Axis axis : this.axis)
            {
               switch(axis.getType())
               {
                  case X_AXIS:
                     if(axis.getValue() > 0)
                     {
                        JHelpGame3DFrame.this.actionKey(ActionKey.LEFT);
                     }
                     else
                     {
                        JHelpGame3DFrame.this.actionKey(ActionKey.RIGHT);
                     }
                  break;
                  case Y_AXIS:
                     if(axis.getValue() > 0)
                     {
                        JHelpGame3DFrame.this.actionKey(ActionKey.UP);
                     }
                     else
                     {
                        JHelpGame3DFrame.this.actionKey(ActionKey.DOWN);
                     }
                  break;
                  default:
                  break;
               }
            }
         }

         synchronized(this.buttons)
         {
            for(final ButtonType type : this.buttons)
            {
               switch(type)
               {
                  case BUTTON_UP:
                     JHelpGame3DFrame.this.actionKey(ActionKey.MENU);
                  break;
                  case BUTTON_LEFT:
                     JHelpGame3DFrame.this.actionKey(ActionKey.SPECIAL);
                  break;
                  case BUTTON_RIGHT:
                     JHelpGame3DFrame.this.actionKey(ActionKey.CANCEL);
                  break;
                  case BUTTON_DOWN:
                     JHelpGame3DFrame.this.actionKey(ActionKey.ACTION);
                  break;
                  case SIDE_LEFT_UP:
                     JHelpGame3DFrame.this.actionKey(ActionKey.BACKWARD);
                  break;
                  case SIDE_RIGHT_UP:
                     JHelpGame3DFrame.this.actionKey(ActionKey.FORWARD);
                  break;
                  case SIDE_LEFT_DOWN:
                     JHelpGame3DFrame.this.actionKey(ActionKey.ROTATE_LEFT);
                  break;
                  case SIDE_RIGHT_DOWN:
                     JHelpGame3DFrame.this.actionKey(ActionKey.ROTATE_RIGHT);
                  break;
                  default:
                  break;
               }
            }
         }
      }

      /**
       * Called when joystick axis activated <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @param joystick
       *           Joystick
       * @param axis
       *           Axis
       * @see jhelp.linux.joystick.JoystickListener#joystickAxisActivated(jhelp.linux.joystick.Joystick,
       *      jhelp.linux.joystick.Axis)
       */
      @Override
      public void joystickAxisActivated(final Joystick joystick, final Axis axis)
      {
         if(this.gameMode == false)
         {
            this.consumeAll();

            switch(axis.getType())
            {
               case X_AXIS:
                  if(axis.getValue() > 0)
                  {
                     JHelpGame3DFrame.this.actionKey(ActionKey.LEFT);
                  }
                  else
                  {
                     JHelpGame3DFrame.this.actionKey(ActionKey.RIGHT);
                  }
               break;
               case Y_AXIS:
                  if(axis.getValue() > 0)
                  {
                     JHelpGame3DFrame.this.actionKey(ActionKey.UP);
                  }
                  else
                  {
                     JHelpGame3DFrame.this.actionKey(ActionKey.DOWN);
                  }
               break;
               default:
               break;
            }

            return;
         }

         synchronized(this.axis)
         {
            this.axis.add(axis);
         }
      }

      /**
       * Called when joystick axis released <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @param joystick
       *           Joystick
       * @param axis
       *           Axis
       * @see jhelp.linux.joystick.JoystickListener#joystickAxisReleased(jhelp.linux.joystick.Joystick,
       *      jhelp.linux.joystick.Axis)
       */
      @Override
      public void joystickAxisReleased(final Joystick joystick, final Axis axis)
      {
         if(this.gameMode == false)
         {
            this.consumeAll();
            return;
         }

         synchronized(this.axis)
         {
            this.axis.remove(axis);
         }
      }

      /**
       * Called when joystick button pressed <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @param joystick
       *           Joystick
       * @param button
       *           Button
       * @see jhelp.linux.joystick.JoystickListener#joystickButtonPressed(jhelp.linux.joystick.Joystick,
       *      jhelp.linux.joystick.Button)
       */
      @Override
      public void joystickButtonPressed(final Joystick joystick, final Button button)
      {
         if(this.gameMode == false)
         {
            this.consumeAll();

            switch(button.getType())
            {
               case BUTTON_UP:
                  JHelpGame3DFrame.this.actionKey(ActionKey.MENU);
               break;
               case BUTTON_LEFT:
                  JHelpGame3DFrame.this.actionKey(ActionKey.SPECIAL);
               break;
               case BUTTON_RIGHT:
                  JHelpGame3DFrame.this.actionKey(ActionKey.CANCEL);
               break;
               case BUTTON_DOWN:
                  JHelpGame3DFrame.this.actionKey(ActionKey.ACTION);
               break;
               case SIDE_LEFT_UP:
                  JHelpGame3DFrame.this.actionKey(ActionKey.BACKWARD);
               break;
               case SIDE_RIGHT_UP:
                  JHelpGame3DFrame.this.actionKey(ActionKey.FORWARD);
               break;
               case SIDE_LEFT_DOWN:
                  JHelpGame3DFrame.this.actionKey(ActionKey.ROTATE_LEFT);
               break;
               case SIDE_RIGHT_DOWN:
                  JHelpGame3DFrame.this.actionKey(ActionKey.ROTATE_RIGHT);
               break;
               default:
               break;
            }

            return;
         }

         synchronized(this.buttons)
         {
            this.buttons.add(button.getType());
         }
      }

      /**
       * Called when joystick button released <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @param joystick
       *           Joystick
       * @param button
       *           Button
       * @see jhelp.linux.joystick.JoystickListener#joystickButtonReleased(jhelp.linux.joystick.Joystick,
       *      jhelp.linux.joystick.Button)
       */
      @Override
      public void joystickButtonReleased(final Joystick joystick, final Button button)
      {
         if(this.gameMode == false)
         {
            this.consumeAll();
            return;
         }

         synchronized(this.buttons)
         {
            this.buttons.remove(button.getType());
         }
      }

      /**
       * Called when user clicked on 2D object <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @param object2d
       *           Object clicked
       * @param x
       *           Relative X
       * @param y
       *           Relative Y
       * @param leftButton
       *           Indicates if left button down
       * @param rightButton
       *           Indicates if right button down
       * @see jhelp.engine.event.Object2DListener#mouseClick(jhelp.engine.twoD.Object2D, int, int, boolean, boolean)
       */
      @Override
      public void mouseClick(final Object2D object2d, final int x, final int y, final boolean leftButton, final boolean rightButton)
      {
         JHelpGame3DFrame.this.clickOnOptionPane(x, y);
      }

      /**
       * Called when user dragged on 2D object <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @param object2d
       *           Object dragged
       * @param x
       *           Relative X
       * @param y
       *           Relative Y
       * @param leftButton
       *           Indicates if left button down
       * @param rightButton
       *           Indicates if right button down
       * @see jhelp.engine.event.Object2DListener#mouseDrag(jhelp.engine.twoD.Object2D, int, int, boolean, boolean)
       */
      @Override
      public void mouseDrag(final Object2D object2d, final int x, final int y, final boolean leftButton, final boolean rightButton)
      {
      }

      /**
       * Called when user entered on 2D object <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @param object2d
       *           Object entered
       * @param x
       *           Relative X
       * @param y
       *           Relative Y
       * @see jhelp.engine.event.Object2DListener#mouseEnter(jhelp.engine.twoD.Object2D, int, int)
       */
      @Override
      public void mouseEnter(final Object2D object2d, final int x, final int y)
      {
      }

      /**
       * Called when user exited from 2D object <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @param object2d
       *           Object exited
       * @param x
       *           Relative X
       * @param y
       *           Relative Y
       * @see jhelp.engine.event.Object2DListener#mouseExit(jhelp.engine.twoD.Object2D, int, int)
       */
      @Override
      public void mouseExit(final Object2D object2d, final int x, final int y)
      {
      }

      /**
       * Called when user moved over on 2D object <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @param object2d
       *           Object
       * @param x
       *           Relative X
       * @param y
       *           Relative Y
       * @see jhelp.engine.event.Object2DListener#mouseMove(jhelp.engine.twoD.Object2D, int, int)
       */
      @Override
      public void mouseMove(final Object2D object2d, final int x, final int y)
      {
      }

      /**
       * Change game mode.<br>
       * In game mode events are reports often.<br>
       * In UI mode events are reports only when changed
       * 
       * @param gameMode
       *           New game mode
       */
      public void setGameMode(final boolean gameMode)
      {
         this.gameMode = gameMode;
      }
   }

   /** Area for option pane cancel button */
   private final Rectangle            cancelArea;
   /** Current cursor visibility */
   private boolean                    cursorVisibility;
   /** Object 2D for show character dialog */
   private final Object2D             dialog;
   /** Texture draw on character dialog */
   private final TextureAlphabetText  dialogTexture;
   /** Edit text in 3D */
   private final EditText3D           editText3D;
   /** Joystick used */
   private final Joystick             joystick;
   /** Manager joystick events */
   private final JoystickEventManager joystickEventManager;
   /** Area for option pane no button */
   private final Rectangle            noArea;
   /** Area for option pane OK button */
   private final Rectangle            okArea;
   /** Object 2D for option pane */
   private final Object2D             optionPane;
   /** Alphabet text used by option pane */
   private final AlphabetText         optionPaneAlphabetText;
   /** Alphabet used by option pane button */
   private final Alphabet             optionPaneButtonsAlphabet;
   /** Current option pane ID */
   private int                        optionPaneID;
   /** Texture used byu option pane */
   private final Texture              optionPaneTexture;
   /** Area for option pane yes button */
   private final Rectangle            yesArea;

   /**
    * Create a new instance of JHelpGame3DFrame
    * 
    * @param gameName
    *           Game name
    * @throws HeadlessException
    *            If OS can't create frame
    */
   public JHelpGame3DFrame(final String gameName)
         throws HeadlessException
   {
      super(gameName);
      this.joystickEventManager = new JoystickEventManager();

      if(JoystickManager.JOYSTICK_MANAGER.numberOfJoysticks() > 0)
      {
         this.joystick = JoystickManager.JOYSTICK_MANAGER.obtainJoystick(0);
         this.joystick.registerJoystickListener(this.joystickEventManager);
      }
      else
      {
         this.joystick = null;
      }

      final Material material = Material.obtainMaterialOrCreate("EditText3D");
      Texture texture = ResourcesGame3D.obtainTexture("floor068.jpg");
      material.setTextureDiffuse(texture);
      texture = ResourcesGame3D.obtainTexture("emerald_bk.jpg");
      material.setTextureSpheric(texture);
      material.setSphericRate(0.5f);

      final JHelpSceneRenderer sceneRenderer = this.getSceneRenderer();
      this.editText3D = new EditText3D(sceneRenderer.getScene(), material, "", false);
      this.editText3D.setPosition(0, 0, -14);

      final int width = this.getWidth();
      final int height = this.getHeight();
      Dimension dimension = AlphabetText.obtainNumberColumnsLines(AlphabetOrange16x16.ALPHABET_ORANGE16X16, width >> 1, height >> 1);
      this.dialogTexture = new TextureAlphabetText(AlphabetOrange16x16.ALPHABET_ORANGE16X16, dimension.width - 2, 8, "", JHelpTextAlign.LEFT, 0xFF0A7E07,
            0x800A7E07, new Insets(32, 0, 0, 0));
      int w = this.dialogTexture.getWidth() << 1;
      int h = this.dialogTexture.getHeight() << 1;
      this.dialog = new Object2D((width - w) >> 1, height - h - 4, w, h);
      this.dialog.setTexture(this.dialogTexture);
      this.dialog.setVisible(false);
      final GUI2D gui2d = sceneRenderer.getGui2d();
      gui2d.addOver3D(this.dialog);

      dimension = AlphabetText.obtainNumberColumnsLines(AlphabetGreen8x16.ALPHABET_GREEN8X16, width >> 2, height >> 2);
      this.optionPaneAlphabetText = new AlphabetText(AlphabetGreen8x16.ALPHABET_GREEN8X16, dimension.width, 16, "", JHelpTextAlign.CENTER, 0xFF0A7E07,
            0x800A7E07);
      final JHelpImage image = this.optionPaneAlphabetText.getImage();
      this.optionPaneButtonsAlphabet = AlphabetGraphitti.NORMAL;
      dimension = this.optionPaneButtonsAlphabet.getCharacterDimension();
      this.optionPaneTexture = new Texture("Game3DOptionPane", image.getWidth(), image.getHeight() + dimension.height + 8);
      this.optionPaneTexture.setAutoFlush(false);
      w = this.optionPaneTexture.getWidth() << 1;
      h = this.optionPaneTexture.getHeight() << 1;
      this.optionPane = new Object2D((width - w) >> 1, (height - h) >> 1, w, h);
      this.optionPane.setTexture(this.optionPaneTexture);
      this.optionPane.setVisible(false);
      gui2d.addOver3D(this.optionPane);
      this.okArea = new Rectangle(-1, -1, -1, -1);
      this.yesArea = new Rectangle(-1, -1, -1, -1);
      this.noArea = new Rectangle(-1, -1, -1, -1);
      this.cancelArea = new Rectangle(-1, -1, -1, -1);
      this.optionPane.addObject2DListener(this.joystickEventManager);

      this.setCursor(ResourcesGame3D.CURSOR_TRANSPARENT);
      this.cursorVisibility = false;
   }

   /**
    * Create an option pane button
    * 
    * @param text
    *           Button text
    * @return Created button
    */
   private JHelpImage createOptionPaneButton(final String text)
   {
      final Dimension dimension = this.optionPaneButtonsAlphabet.computeTextDimension(text);
      final JHelpImage image = new JHelpImage(dimension.width + 8, dimension.height + 8, 0xAAFFFFFF);
      image.startDrawMode();
      image.drawRectangle(0, 0, image.getWidth() - 1, image.getHeight() - 1, 0xFF000000);
      this.optionPaneButtonsAlphabet.drawOn(text, JHelpTextAlign.CENTER, image, image.getWidth() >> 1, image.getHeight() >> 1, true);
      image.endDrawMode();
      return image;
   }

   /**
    * Update option pane
    * 
    * @param text
    *           New text
    * @param optionPaneButtons
    *           Option pane options. Must be one of : {@link OptionPaneButtons#OK}, {@link OptionPaneButtons#YES_NO} or
    *           {@link OptionPaneButtons#YES_NO_CANCEL}
    */
   private void updateOptionPane(final String text, final OptionPaneButtons optionPaneButtons)
   {
      if(text == null)
      {
         throw new NullPointerException("text musn't be null");
      }

      if(optionPaneButtons.validForSpecifyOptionPaneButtons() == false)
      {
         throw new IllegalArgumentException(optionPaneButtons + " not valid");
      }

      this.okArea.setBounds(-1, -1, -1, -1);
      this.yesArea.setBounds(-1, -1, -1, -1);
      this.noArea.setBounds(-1, -1, -1, -1);
      this.cancelArea.setBounds(-1, -1, -1, -1);
      this.optionPaneAlphabetText.setText(text);
      this.optionPaneTexture.clear(new Color(0x44123456, true));
      this.optionPaneTexture.drawImage(0, 0, this.optionPaneAlphabetText.getImage());
      final int width = this.optionPaneTexture.getWidth();
      final int height = this.optionPaneTexture.getHeight();
      int x1, x2, x3;
      int space;
      JHelpImage imageButton1, imageButton2, imageButton3;

      switch(optionPaneButtons)
      {
         case OK:
            imageButton1 = this.createOptionPaneButton(ResourcesGUI.RESOURCE_TEXT.getText(ResourcesGUI.OPTION_PANE_OK));
            this.optionPaneTexture.drawImage((width - imageButton1.getWidth()) >> 1, height - imageButton1.getHeight(), imageButton1);
            this.okArea.setBounds((width - imageButton1.getWidth()) >> 1, height - imageButton1.getHeight(), imageButton1.getWidth(), imageButton1.getHeight());
         break;
         case YES_NO:
            imageButton1 = this.createOptionPaneButton(ResourcesGUI.RESOURCE_TEXT.getText(ResourcesGUI.OPTION_PANE_YES));
            imageButton2 = this.createOptionPaneButton(ResourcesGUI.RESOURCE_TEXT.getText(ResourcesGUI.OPTION_PANE_NO));
            space = (width - imageButton1.getWidth() - imageButton2.getWidth()) / 3;
            x1 = space;
            x2 = x1 + imageButton1.getWidth() + space;
            this.optionPaneTexture.drawImage(x1, height - imageButton1.getHeight(), imageButton1);
            this.optionPaneTexture.drawImage(x2, height - imageButton2.getHeight(), imageButton2);
            this.yesArea.setBounds(x1, height - imageButton1.getHeight(), imageButton1.getWidth(), imageButton1.getHeight());
            this.noArea.setBounds(x2, height - imageButton2.getHeight(), imageButton2.getWidth(), imageButton2.getHeight());
         break;
         case YES_NO_CANCEL:
            imageButton1 = this.createOptionPaneButton(ResourcesGUI.RESOURCE_TEXT.getText(ResourcesGUI.OPTION_PANE_YES));
            imageButton2 = this.createOptionPaneButton(ResourcesGUI.RESOURCE_TEXT.getText(ResourcesGUI.OPTION_PANE_NO));
            imageButton3 = this.createOptionPaneButton(ResourcesGUI.RESOURCE_TEXT.getText(ResourcesGUI.OPTION_PANE_CANCEL));
            space = (width - imageButton1.getWidth() - imageButton2.getWidth() - imageButton3.getWidth()) >> 2;
            x1 = space;
            x2 = x1 + imageButton1.getWidth() + space;
            x3 = x2 + imageButton2.getWidth() + space;
            this.optionPaneTexture.drawImage(x1, height - imageButton1.getHeight(), imageButton1);
            this.optionPaneTexture.drawImage(x2, height - imageButton2.getHeight(), imageButton2);
            this.optionPaneTexture.drawImage(x3, height - imageButton3.getHeight(), imageButton3);
            this.yesArea.setBounds(x1, height - imageButton1.getHeight(), imageButton1.getWidth(), imageButton1.getHeight());
            this.noArea.setBounds(x2, height - imageButton2.getHeight(), imageButton2.getWidth(), imageButton2.getHeight());
            this.cancelArea.setBounds(x3, height - imageButton3.getHeight(), imageButton3.getWidth(), imageButton3.getHeight());
         break;
         default:
         break;
      }

      this.optionPaneTexture.flush();
   }

   /**
    * Called when user click somewhere on option pane
    * 
    * @param x
    *           X on option pane
    * @param y
    *           Y on option pane
    */
   void clickOnOptionPane(int x, int y)
   {
      x >>= 1;
      y >>= 1;
      OptionPaneButtons optionPaneButtons;

      if(this.okArea.contains(x, y) == true)
      {
         optionPaneButtons = OptionPaneButtons.OK;
      }
      else if(this.yesArea.contains(x, y) == true)
      {
         optionPaneButtons = OptionPaneButtons.YES;
      }
      else if(this.noArea.contains(x, y) == true)
      {
         optionPaneButtons = OptionPaneButtons.NO;
      }
      else if(this.cancelArea.contains(x, y) == true)
      {
         optionPaneButtons = OptionPaneButtons.CANCEL;
      }
      else
      {
         return;
      }

      this.getSceneRenderer().getGui2d().allCanBeDetected();
      this.joystickEventManager.setGameMode(true);
      this.optionPane.setVisible(false);
      this.optionPaneButtonClicked(optionPaneButtons, this.optionPaneID);
      this.setCursorVisiblity(false);
   }

   /**
    * Called when user triggered an action key (By key board or joystick)
    * 
    * @param actionKey
    *           Action key happen
    */
   protected abstract void actionKeyInternal(final ActionKey actionKey);

   /**
    * Called when current character dialog is closed
    */
   protected abstract void characterDialogClose();

   /**
    * Called when a character is add to edit text <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param character
    *           Character added
    * @param cursor
    *           Cursor position
    * @param text
    *           Text to draw
    * @see jhelp.engine.gui.Game3DFrame#editTextAdd(char, int, java.lang.String)
    */
   @Override
   protected final void editTextAdd(final char character, final int cursor, final String text)
   {
      this.editText3D.setText(text, cursor);
   }

   /**
    * Called when cursor moved in edit text <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param character
    *           Character under the cursor
    * @param cursor
    *           Cursor position
    * @param text
    *           Text to draw
    * @see jhelp.engine.gui.Game3DFrame#editTextCursorMoved(char, int, java.lang.String)
    */
   @Override
   protected final void editTextCursorMoved(final char character, final int cursor, final String text)
   {
      this.editText3D.setText(text, cursor);
   }

   /**
    * Called when user delete character <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param character
    *           Deleted character
    * @param cursor
    *           Cursor position
    * @param text
    *           Text to draw
    * @see jhelp.engine.gui.Game3DFrame#editTextDelete(char, int, java.lang.String)
    */
   @Override
   protected final void editTextDelete(final char character, final int cursor, final String text)
   {
      this.editText3D.setText(text, cursor);
   }

   /**
    * Called when edition is finished <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param text
    *           Final text
    * @see jhelp.engine.gui.Game3DFrame#editTextEnd(java.lang.String)
    */
   @Override
   protected final void editTextEnd(final String text)
   {
      this.editText3D.setVisible(false);
      this.joystickEventManager.setGameMode(true);
      this.textTyped(text);
   }

   /**
    * Called just before end edition to know witch mode go after the edition <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param text
    *           Current text
    * @return Mode to go after
    * @see jhelp.engine.gui.Game3DFrame#editTextKeyboardModeAfterEnter(java.lang.String)
    */
   @Override
   protected final KeyboardMode editTextKeyboardModeAfterEnter(final String text)
   {
      return KeyboardMode.GAME;
   }

   /**
    * Called when edit text started <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @see jhelp.engine.gui.Game3DFrame#editTextStart()
    */
   @Override
   protected final void editTextStart()
   {
      this.joystickEventManager.setGameMode(false);
      this.editText3D.setText("");
      this.editText3D.setVisible(true);
   }

   /**
    * Called when user select an option pane button
    * 
    * @param optionPaneButtons
    *           Option pane button choose : {@link OptionPaneButtons#CANCEL}, {@link OptionPaneButtons#YES},
    *           {@link OptionPaneButtons#NO} OR {@link OptionPaneButtons#OK}
    * @param optionPaneID
    *           Option pane ID
    */
   protected abstract void optionPaneButtonClicked(OptionPaneButtons optionPaneButtons, int optionPaneID);

   /**
    * Called when edit text finished
    * 
    * @param text
    *           Text typed
    */
   protected abstract void textTyped(String text);

   /**
    * Called on user action key <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param actionKey
    *           Action key happen
    * @see jhelp.engine.gui.Game3DFrame#actionKey(jhelp.engine.gui.ActionKey)
    */
   @Override
   public final void actionKey(final ActionKey actionKey)
   {
      if(this.dialog.isVisible() == true)
      {
         switch(actionKey)
         {
            case ACTION:
               if(this.dialogTexture.hasNext() == true)
               {
                  this.dialogTexture.next();
               }
               else
               {
                  this.hideDialogText();
               }
            break;
            case CANCEL:
               if(this.dialogTexture.hasPrevious() == true)
               {
                  this.dialogTexture.previous();
               }
            break;
            default:
            break;
         }

         return;
      }

      this.actionKeyInternal(actionKey);
   }

   /**
    * Return to game mode.<br>
    * In game mode, action keys are repeat often
    */
   public final void backToGame()
   {
      this.setKeyboardMode(KeyboardMode.GAME);
      this.joystickEventManager.setGameMode(true);
   }

   /**
    * Hide character dialog text
    */
   public final void hideDialogText()
   {
      this.dialog.setVisible(false);
      this.joystickEventManager.setGameMode(true);
      this.characterDialogClose();
   }

   /**
    * Indicates if mouse cursor is visible
    * 
    * @return {@code true} if mouse cursor is visible
    */
   public final boolean isCursorVisible()
   {
      return this.cursorVisibility;
   }

   /**
    * Cahnge mouse cursor visibility
    * 
    * @param visible
    *           New mouse cursor visibility
    */
   public final void setCursorVisiblity(final boolean visible)
   {
      if(visible == this.cursorVisibility)
      {
         return;
      }

      this.cursorVisibility = visible;

      if(visible == true)
      {
         this.setCursor(ResourcesGame3D.CURSOR_HAND);
      }
      else
      {
         this.setCursor(ResourcesGame3D.CURSOR_TRANSPARENT);
      }
   }

   /**
    * Show a character dialog
    * 
    * @param text
    *           Dialog text
    * @param characterFace
    *           Character face resource. Use {@code null} if none
    * @param characterName
    *           Character name. Use {@code null} if none
    */
   public final void showCharacterDialog(final String text, final String characterFace, final String characterName)
   {
      if(text == null)
      {
         throw new NullPointerException("text musn't be null");
      }

      this.joystickEventManager.setGameMode(false);
      this.dialogTexture.setText(text);
      final JHelpImage image = this.dialogTexture.getEmbedImage();
      image.startDrawMode();
      image.fillRectangle(0, 0, image.getWidth(), 32, 0xCCCECAFE, false);

      if(characterFace != null)
      {
         image.drawImage(0, 0, ResourcesGame3D.obtainFace(characterFace));
      }

      if(characterName != null)
      {
         AlphabetBlue16x16.ALPHABET_BLUE16X16.drawOn(characterName, JHelpTextAlign.LEFT, image, 34, 8);
      }

      image.endDrawMode();
      this.dialogTexture.refresh();

      this.dialog.setVisible(true);
   }

   /**
    * Show option pane
    * 
    * @param text
    *           Option pane text
    * @param optionPaneButtons
    *           Option pane buttons : must be : {@link OptionPaneButtons#OK}, {@link OptionPaneButtons#YES_NO} or
    *           {@link OptionPaneButtons#YES_NO_CANCEL}
    * @param optionPaneID
    *           Option pane ID
    */
   public void showOptionPane(final String text, final OptionPaneButtons optionPaneButtons, final int optionPaneID)
   {
      this.setCursorVisiblity(true);
      UtilGUI.locateMouseOver(this);
      this.updateOptionPane(text, optionPaneButtons);
      this.getSceneRenderer().getGui2d().setExclusiveDetection(this.optionPane);
      this.joystickEventManager.setGameMode(false);
      this.optionPane.setVisible(true);
      this.optionPaneID = optionPaneID;
   }

   /**
    * Start to capture next key to have its key code.<br>
    * See {@link #keyCodeCaptured(int)} to get the result
    */
   public final void startCaptureNextKey()
   {
      this.setKeyboardMode(KeyboardMode.CAPTURE_KEY_CODE);
   }

   /**
    * Start edit text.<br>
    * See {@link #textTyped(String)} to have the typed text
    */
   public final void startEditText()
   {
      this.setKeyboardMode(KeyboardMode.EDIT_TEXT);
   }

   /**
    * Go in UI interface mode. <br>
    * In UI interface mode action are report only when they happen. By example down on joystick button report once down, but
    * have to up and down it again to have a new report
    */
   public final void uiInterface()
   {
      this.joystickEventManager.setGameMode(false);
   }
}