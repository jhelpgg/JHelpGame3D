package jhelp.game3d.resources;

import jhelp.engine.Texture;

/**
 * Eyes texture description
 * 
 * @author JHelp
 */
public enum EyesTexture
{
   /** Blue eye */
   EYE_BLUE("eyeBlue.png"),
   /** Blue eye 2 */
   EYE_BLUE_2("eyeBlue2.png"),
   /** Blue eye 3 */
   EYE_BLUE_3("eyeBlue3.png"),
   /** Brown eye */
   EYE_BROWN("eyeBrown.png"),
   /** Brown eye 2 */
   EYE_BROWN_2("eyeBrown2.png"),
   /** Green eye */
   EYE_GREEN("eyeGreen.png"),
   /** Green eye 2 */
   EYE_GREEN_2("eyeGreen2.png"),
   /** Green eye 3 */
   EYE_GREEN_3("eyeGreen3.png"),
   /** Shine green eye */
   EYE_GREEN_SHINE("eyeGreenShine.png"),
   /** Light red eye */
   EYE_LIGHT_RED("eyeLightRed.png"),
   /** Pink eye */
   EYE_PINK("eyePink.png"),
   /** Purple eye */
   EYE_PURPLE("eyePurple.png"),
   /** Red eye */
   EYE_RED("eyeRed.png"),
   /** Red eye 2 */
   EYE_RED_2("eyeRed2.png"),
   /** Red eye 3 */
   EYE_RED_3("eyeRed3.png"),
   /** Blue eye with tone inside */
   EYE_TONE_BLUE("eyeToneBlue.png"),
   /** Red eye with tone inside */
   EYE_TONE_RED("eyeToneRed.png");

   /** Texture name */
   private final String name;

   /**
    * Create a new instance of EyesTexture
    * 
    * @param name
    *           Texture name
    */
   EyesTexture(final String name)
   {
      this.name = name;
   }

   /**
    * Texture name
    * 
    * @return Texture name
    */
   public String getName()
   {
      return this.name;
   }

   /**
    * Associated texture
    * 
    * @return Associated texture
    */
   public Texture getTexture()
   {
      return ResourcesGame3D.obtainEyeTexture(this);
   }
}