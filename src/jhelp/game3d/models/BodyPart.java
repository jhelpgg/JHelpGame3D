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

/**
 * Part of character body
 * 
 * @author JHelp
 */
public enum BodyPart
{
   /** Main body */
   BODY(16),
   /** The dress */
   DRESS(17),
   /** Left eye */
   EYE_LEFT(13),
   /** Right eye */
   EYE_RIGHT(12),
   /** Left feet */
   FEET_LEFT(1),
   /** Right feet */
   FEET_RIGHT(0),
   /** Hair */
   HAIR(14),
   /** Left hand */
   HAND_LEFT(7),
   /** Right hand */
   HAND_RIGHT(6),
   /** Left harm "high" part (join to body) */
   HARM_HIGH_LEFT(11),
   /** Right harm "high" part (join to body) */
   HARM_HIGH_RIGHT(10),
   /** Left harm "low" part */
   HARM_LOW_LEFT(9),
   /** Right harm "low" part */
   HARM_LOW_RIGHT(8),
   /** Head */
   HEAD(15),
   /** Left leg "high" part (join to body) */
   LEG_HIGH_LEFT(5),
   /** Right leg "high" part (join to body) */
   LEG_HIGH_RIGHT(4),
   /** Left leg "low" part */
   LEG_LOW_LEFT(3),
   /** Right leg "low" part */
   LEG_LOW_RIGHT(2);

   /**
    * Search body part by its ID
    * 
    * @param areaID
    *           Body part ID
    * @return Body part
    */
   public static BodyPart obtainBodyPartByID(final int areaID)
   {
      for(final BodyPart bodyPart : BodyPart.values())
      {
         if(bodyPart.areaID == areaID)
         {
            return bodyPart;
         }
      }

      return null;
   }

   /** Body part ID */
   private final int areaID;

   /**
    * Create a new instance of BodyPart
    * 
    * @param areaID
    *           Body part ID
    */
   BodyPart(final int areaID)
   {
      this.areaID = areaID;
   }

   /**
    * Body part ID
    * 
    * @return Body part ID
    */
   public int getAreaID()
   {
      return this.areaID;
   }
}