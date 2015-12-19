/**
 * <h1>License :</h1> <br>
 * The following code is deliver as is. I take care that code compile and work, but I am not responsible about any damage it may
 * cause.<br>
 * You can use, modify, the code as your need for any usage. But you can't do any action that avoid me or other person use,
 * modify this code. The code is free for usage and modification, you can't change that fact.<br>
 * <br>
 * 
 * @author JHelp
 */
package jhelp.game3d.models;

import jhelp.engine.Animation;
import jhelp.engine.Node;

/**
 * Generic character with "common" animation
 * 
 * @author JHelp
 */
public interface Character
{
   /**
    * Main character node
    * 
    * @return Main character node
    */
   public Node getCharacterNode();

   /**
    * Number of specific animation.<br>
    * Specific animation are additional animation for the character type
    * 
    * @return Number of specific animation.
    */
   public int numberOfSpecificAnimation();

   /**
    * Obtain animation for put the character at start position
    * 
    * @param frame
    *           Number of frame for do the animation
    * @return Animation for put the character at start position
    */
   public Animation obtainPutAtStartPositionAnimation(int frame);

   /**
    * Obtain animation for run
    * 
    * @return Animation for run
    */
   public Animation obtainRunAnimation();

   /**
    * Obtain a specific animation
    * 
    * @param animationIndex
    *           Animation ID
    * @return Specific animation
    */
   public Animation obtainSpecificAnimation(int animationIndex);

   /**
    * Obtain animation for walk fast pace
    * 
    * @return Animation for walk fast pace
    */
   public Animation obtainWalkFastAnimation();

   /**
    * Obtain animation for walk normal pace
    * 
    * @return Animation for walk normal pace
    */
   public Animation obtainWalkNormalAnimation();

   /**
    * Obtain animation for walk slow pace
    * 
    * @return Animation for walk slow pace
    */
   public Animation obtainWalkSlowAnimation();
}