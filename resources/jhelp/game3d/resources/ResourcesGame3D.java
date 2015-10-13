package jhelp.game3d.resources;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import jhelp.engine.Texture;
import jhelp.sound.JHelpSound;
import jhelp.sound.SoundFactory;
import jhelp.util.cache.Cache;
import jhelp.util.cache.CacheElement;
import jhelp.util.debug.Debug;
import jhelp.util.gui.JHelpImage;
import jhelp.util.resources.ResourceText;
import jhelp.util.resources.Resources;

/**
 * Access to common games resources
 * 
 * @author JHelp
 */
public class ResourcesGame3D
{
   /**
    * Images cache
    * 
    * @author JHelp
    */
   private static class CacheImageElement
         extends CacheElement<JHelpImage>
   {
      /** Image forced height. -1 means as original */
      private final int    height;
      /** Image path */
      private final String path;
      /** Image forced width. -1 means as original */
      private final int    width;

      /**
       * Create a new instance of CacheImageElement
       * 
       * @param path
       *           Image path
       * @param width
       *           Image forced width. -1 means as original
       * @param height
       *           Image forced height. -1 means as original
       */
      CacheImageElement(final String path, final int width, final int height)
      {
         this.path = path;
         this.width = width;
         this.height = height;
      }

      /**
       * Create the image <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @return Created image
       * @see jhelp.util.cache.CacheElement#createElement()
       */
      @Override
      protected JHelpImage createElement()
      {
         if((this.width > 0) && (this.height > 0))
         {
            return ResourcesGame3D.RESOURCES.obtainResizedJHelpImage(this.path, this.width, this.height);
         }

         return ResourcesGame3D.RESOURCES.obtainJHelpImage(this.path);
      }
   }

   /** Images cache */
   private static final Cache<JHelpImage> CACHE_IMAGES;
   /** Relative path for faces */
   private static final String            PATH_FACES          = "images/faces/";
   /** Relative path for icons */
   private static final String            PATH_ICONS          = "images/icons/";
   /** Relative path for ambient sounds */
   private static final String            PATH_SOUNDS_AMBIENT = "sounds/ambient/";
   /** Relative path for FX sounds */
   private static final String            PATH_SOUNDS_FX      = "sounds/fx/";
   /** Relative path for textures */
   private static final String            PATH_TEXTURES       = "images/textures/";
   /** Access to texts */
   private static final ResourceText      RESOURCE_TEXT;
   /** Access to resources */
   static final Resources                 RESOURCES;
   /** Hand cursor */
   public static final Cursor             CURSOR_HAND;
   /** Transparent cursor */
   public static final Cursor             CURSOR_TRANSPARENT;
   /** Text key for ask to exit */
   public static final String             TEXT_EXIT_NOW       = "exitNow";

   static
   {
      RESOURCES = new Resources(ResourcesGame3D.class);
      RESOURCE_TEXT = ResourcesGame3D.RESOURCES.obtainResourceText("texts/texts");
      CACHE_IMAGES = new Cache<JHelpImage>();
      CURSOR_HAND = ResourcesGame3D.createCursor("cursor.png");
      CURSOR_TRANSPARENT = ResourcesGame3D.createCursor(null);
   }

   /**
    * Create a cursor
    * 
    * @param name
    *           Cursor name in icons. Use {@code null} for transparent cursor
    * @return Created cursor
    */
   private static Cursor createCursor(String name)
   {
      final Dimension dimension = Toolkit.getDefaultToolkit().getBestCursorSize(32, 32);

      if((dimension == null) || (dimension.width <= 0) || (dimension.height <= 0))
      {
         return Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
      }

      BufferedImage bufferedImage;

      if(name == null)
      {
         bufferedImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
         final int[] pixels = new int[dimension.width * dimension.height];
         bufferedImage.setRGB(0, 0, dimension.width, dimension.height, pixels, 0, dimension.width);
         bufferedImage.flush();
         name = "TRANSPARENT";
      }
      else
      {
         try
         {
            final BufferedImage temporary = ResourcesGame3D.RESOURCES.obtainBufferedImage(ResourcesGame3D.PATH_ICONS + name);

            if((temporary.getWidth() == dimension.width) && (temporary.getHeight() == dimension.height))
            {
               bufferedImage = temporary;
            }
            else
            {
               bufferedImage = new BufferedImage(dimension.width, dimension.height, temporary.getType());
               final Graphics2D graphics2d = bufferedImage.createGraphics();
               graphics2d.drawImage(temporary, 0, 0, dimension.width, dimension.height, null);
               graphics2d.dispose();
               bufferedImage.flush();
            }
         }
         catch(final Exception exception)
         {
            Debug.printException(exception, "Failed to load image : ", name);
            return Cursor.getDefaultCursor();
         }
      }

      return Toolkit.getDefaultToolkit().createCustomCursor(bufferedImage, new Point(bufferedImage.getWidth() >> 1, bufferedImage.getHeight() >> 1), name);
   }

   /**
    * Obtain an image
    * 
    * @param path
    *           Image path
    * @return Image
    */
   private static JHelpImage obtainImage(final String path)
   {
      return ResourcesGame3D.obtainImage(path, -1, -1);
   }

   /**
    * Obtain a resized image
    * 
    * @param path
    *           Image path
    * @param width
    *           Forced with. Use -1 for no resize
    * @param height
    *           Forced height . Use -1 for no resize
    * @return Image resized
    */
   private static JHelpImage obtainImage(final String path, final int width, final int height)
   {
      return ResourcesGame3D.CACHE_IMAGES.get(path, new CacheImageElement(path, width, height));
   }

   /**
    * Obtain a face
    * 
    * @param name
    *           Face name
    * @return Face image
    */
   public static JHelpImage obtainFace(final String name)
   {
      return ResourcesGame3D.obtainImage(ResourcesGame3D.PATH_FACES + name, 32, 32);
   }

   /**
    * Obtain an icon
    * 
    * @param name
    *           Icon name
    * @return Icon image
    */
   public static JHelpImage obtainIcon(final String name)
   {
      return ResourcesGame3D.obtainImage(ResourcesGame3D.PATH_ICONS + name);
   }

   /**
    * Obtain an ambient sound
    * 
    * @param name
    *           Sound name
    * @return Ambient sound
    */
   public static JHelpSound obtainSoundAmbient(final String name)
   {
      return SoundFactory.getSoundFromResource(ResourcesGame3D.PATH_SOUNDS_AMBIENT + name, ResourcesGame3D.RESOURCES);
   }

   /**
    * Obtain a FX sound
    * 
    * @param name
    *           Sound name
    * @return FX sound
    */
   public static JHelpSound obtainSoundFx(final String name)
   {
      return SoundFactory.getSoundFromResource(ResourcesGame3D.PATH_SOUNDS_FX + name, ResourcesGame3D.RESOURCES);
   }

   /**
    * Obtain a text
    * 
    * @param textKey
    *           text key
    * @return Text
    */
   public static String obtainText(final String textKey)
   {
      return ResourcesGame3D.RESOURCE_TEXT.getText(textKey);
   }

   /**
    * Obtain a texture
    * 
    * @param name
    *           Texture name
    * @return Texture
    */
   public static Texture obtainTexture(final String name)
   {
      final Texture texture = Texture.obtainTexture(name);

      if(texture != null)
      {
         return texture;
      }

      try
      {
         return new Texture(name, Texture.REFERENCE_RESOURCES, ResourcesGame3D.RESOURCES.obtainResourceStream(ResourcesGame3D.PATH_TEXTURES + name));
      }
      catch(final Exception exception)
      {
         Debug.printException(exception, "Failed to get texture : ", name);
         return Texture.DUMMY;
      }
   }
}