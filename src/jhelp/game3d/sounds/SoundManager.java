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
package jhelp.game3d.sounds;

import java.util.concurrent.atomic.AtomicBoolean;

import jhelp.sound.JHelpSound;
import jhelp.sound.JHelpSoundListener;
import jhelp.util.list.Queue;
import jhelp.util.list.Ring;
import jhelp.util.thread.ThreadManager;
import jhelp.util.thread.ThreadedVerySimpleTask;

/**
 * Game sound manager.<br>
 * It manages ambient and FX sounds.<br>
 * Ambient sounds are played in loop until ring is clear.<br>
 * FX are played once.
 * 
 * @author JHelp
 */
public class SoundManager
{
   /**
    * Task that m
    * 
    * @author JHelp
    */
   private class TaskAmbient
         extends ThreadedVerySimpleTask
         implements JHelpSoundListener
   {
      /**
       * Create a new instance of TaskAmbient
       */
      TaskAmbient()
      {
      }

      /**
       * Manage ring ambient sounds <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @see jhelp.util.thread.ThreadedVerySimpleTask#doVerySimpleAction()
       */
      @Override
      protected void doVerySimpleAction()
      {
         SoundManager.this.nextAmbient();
      }

      /**
       * Called when a sound destroyed <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @param sound
       *           Destroyed sound
       * @see jhelp.sound.JHelpSoundListener#soundDestroy(jhelp.sound.JHelpSound)
       */
      @Override
      public void soundDestroy(final JHelpSound sound)
      {
      }

      /**
       * Called when a sound loop <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @param sound
       *           Looped sound
       * @see jhelp.sound.JHelpSoundListener#soundLoop(jhelp.sound.JHelpSound)
       */
      @Override
      public void soundLoop(final JHelpSound sound)
      {
      }

      /**
       * Called when sound start <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @param sound
       *           Started sound
       * @see jhelp.sound.JHelpSoundListener#soundStart(jhelp.sound.JHelpSound)
       */
      @Override
      public void soundStart(final JHelpSound sound)
      {
      }

      /**
       * Called when sound stop <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @param sound
       *           Stopped sound
       * @see jhelp.sound.JHelpSoundListener#soundStop(jhelp.sound.JHelpSound)
       */
      @Override
      public void soundStop(final JHelpSound sound)
      {
         sound.removeSoundListener(this);
         ThreadManager.THREAD_MANAGER.doThread(this, null);
      }
   }

   /**
    * Task that manage FX queue
    * 
    * @author JHelp
    */
   private class TaskFX
         extends ThreadedVerySimpleTask
         implements JHelpSoundListener
   {
      /**
       * Create a new instance of TaskFX
       */
      TaskFX()
      {
      }

      /**
       * Manage FX queue <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @see jhelp.util.thread.ThreadedVerySimpleTask#doVerySimpleAction()
       */
      @Override
      protected void doVerySimpleAction()
      {
         SoundManager.this.nextFX();
      }

      /**
       * Called when a sound destroyed <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @param sound
       *           Destroyed sound
       * @see jhelp.sound.JHelpSoundListener#soundDestroy(jhelp.sound.JHelpSound)
       */
      @Override
      public void soundDestroy(final JHelpSound sound)
      {
      }

      /**
       * Called when a sound looped <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @param sound
       *           Looped sound
       * @see jhelp.sound.JHelpSoundListener#soundLoop(jhelp.sound.JHelpSound)
       */
      @Override
      public void soundLoop(final JHelpSound sound)
      {
      }

      /**
       * Called when a sound started <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @param sound
       *           Started sound
       * @see jhelp.sound.JHelpSoundListener#soundStart(jhelp.sound.JHelpSound)
       */
      @Override
      public void soundStart(final JHelpSound sound)
      {
      }

      /**
       * Called when a sound stopped <br>
       * <br>
       * <b>Parent documentation:</b><br>
       * {@inheritDoc}
       * 
       * @param sound
       *           Stopped sound
       * @see jhelp.sound.JHelpSoundListener#soundStop(jhelp.sound.JHelpSound)
       */
      @Override
      public void soundStop(final JHelpSound sound)
      {
         sound.removeSoundListener(this);
         ThreadManager.THREAD_MANAGER.doThread(this, null);
      }
   }

   /** Sound manager singleton instance */
   public static final SoundManager SOUND_MANAGER = new SoundManager();

   /** Ambient sounds ring */
   private final Ring<JHelpSound>   ambientRing;
   /** Current ambient sound */
   private JHelpSound               currentAmbient;
   /** Current FX sound */
   private JHelpSound               currentFX;
   /** FX sounds queue */
   private final Queue<JHelpSound>  fxQueue;
   /** Indicates if one ambient sound playing */
   private final AtomicBoolean      playingAmbient;
   /** Indicates if one FX sound playing */
   private final AtomicBoolean      playingFX;
   /** Task that manages ambient sounds ring */
   private final TaskAmbient        taskAmbient;
   /** Task that manages FX sounds queue */
   private final TaskFX             taskFX;

   /**
    * Create a new instance of SoundManager
    */
   private SoundManager()
   {
      this.playingAmbient = new AtomicBoolean(false);
      this.playingFX = new AtomicBoolean(false);
      this.ambientRing = new Ring<JHelpSound>();
      this.fxQueue = new Queue<JHelpSound>();
      this.taskAmbient = new TaskAmbient();
      this.taskFX = new TaskFX();
   }

   /**
    * Play next ambient sound OR stop management if ring is empty
    */
   void nextAmbient()
   {
      this.playingAmbient.set(false);

      synchronized(this.playingAmbient)
      {
         if(this.ambientRing.isEmpty())
         {
            return;
         }

         this.playingAmbient.set(true);
         this.currentAmbient = this.ambientRing.get();
         this.ambientRing.next();
      }

      this.currentAmbient.addSoundListener(this.taskAmbient);
      this.currentAmbient.play();
   }

   /**
    * Play next FX sound OR stop management if queue is empty
    */
   void nextFX()
   {
      this.playingFX.set(false);

      synchronized(this.playingFX)
      {
         if(this.fxQueue.isEmpty())
         {
            return;
         }

         this.playingFX.set(true);
         this.currentFX = this.fxQueue.outQueue();
      }

      this.currentFX.addSoundListener(this.taskFX);
      this.currentFX.play();
   }

   /**
    * Clear ring ambient sounds
    */
   public void clearAmbient()
   {
      synchronized(this.playingAmbient)
      {
         this.ambientRing.clear();
      }
   }

   /**
    * Play ambient sound
    * 
    * @param sound
    *           Sound to play ambient
    */
   public void playAmbient(final JHelpSound sound)
   {
      synchronized(this.playingAmbient)
      {
         this.ambientRing.add(sound);
      }

      if(!this.playingAmbient.get())
      {
         ThreadManager.THREAD_MANAGER.doThread(this.taskAmbient, null);
      }
   }

   /**
    * Play FX sound
    * 
    * @param sound
    *           Sound to play FX
    */
   public void playFX(final JHelpSound sound)
   {
      synchronized(this.playingFX)
      {
         this.fxQueue.inQueue(sound);
      }

      if(!this.playingFX.get())
      {
         ThreadManager.THREAD_MANAGER.doThread(this.taskFX, null);
      }
   }
}