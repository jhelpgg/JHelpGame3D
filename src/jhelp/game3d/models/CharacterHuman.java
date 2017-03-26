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

import java.util.concurrent.atomic.AtomicInteger;

import jhelp.engine.Animation;
import jhelp.engine.Color4f;
import jhelp.engine.Material;
import jhelp.engine.Node;
import jhelp.engine.NodeWithMaterial;
import jhelp.engine.NodeWithMaterial.TwoSidedState;
import jhelp.engine.Object3D;
import jhelp.engine.Texture;
import jhelp.engine.VirtualBox;
import jhelp.engine.anim.AnimationParallel;
import jhelp.engine.anim.AnimationPositionNode;
import jhelp.engine.geom.Sphere;
import jhelp.engine.util.PositionNode;
import jhelp.game3d.resources.EyesTexture;
import jhelp.game3d.resources.ResourcesGame3D;
import jhelp.util.gui.Color;
import jhelp.util.gui.JHelpImage;

/**
 * Human character
 * 
 * @author JHelp
 */
public abstract class CharacterHuman
      implements Character
{
   /** Next human character ID */
   private static final AtomicInteger NEXT_ID                                       = new AtomicInteger(0);
   /** Time for do a standard expression in frame */
   private static final int           TIME_EXPRESSION                               = 12;
   /** Time for run in frame */
   private static final int           TIME_RUN                                      = (3 * CharacterHuman.TIME_WALK) / 4;
   /** Time for walk in frame */
   private static final int           TIME_WALK                                     = 25;
   /** Animation specific : walk slow pace on moving only legs */
   public static final int            ANIMATION_00_WALK_SLOW_ONLY_LEGS              = 0;
   /** Animation specific : walk normal pace on moving only legs */
   public static final int            ANIMATION_01_WALK_NORMAL_ONLY_LEGS            = 1;
   /** Animation specific : walk fast pace on moving only legs */
   public static final int            ANIMATION_02_WALK_FAST_ONLY_LEGS              = 2;
   /** Animation specific : run on moving only legs */
   public static final int            ANIMATION_03_RUN_ONLY_LEGS                    = 3;
   /** Animation specific : "say" yes with head */
   public static final int            ANIMATION_04_EXPRESSION_YES                   = 4;
   /** Animation specific : "say" no with head */
   public static final int            ANIMATION_05_EXPRESSION_NO                    = 5;
   /** Animation specific : "I don't" know */
   public static final int            ANIMATION_06_EXPRESSION_I_DONT_KNOW           = 6;
   /** Animation specific : "Yeah" (happy) */
   public static final int            ANIMATION_07_EXPRESSION_YEAH                  = 7;
   /** Animation specific : "Yahoo" (very happy) */
   public static final int            ANIMATION_08_EXPRESSION_YAHOO                 = 8;
   /** Animation specific : Make eyes look at right */
   public static final int            ANIMATION_09_EYES_LOOK_AT_RIGHT               = 9;
   /** Animation specific : Make eyes look at left */
   public static final int            ANIMATION_10_EYES_LOOK_AT_LEFT                = 10;
   /** Animation specific : Make eyes look at up */
   public static final int            ANIMATION_11_EYES_LOOK_AT_UP                  = 11;
   /** Animation specific : Make eyes look at down */
   public static final int            ANIMATION_12_EYES_LOOK_AT_DOWN                = 12;
   /** squint to noise */
   public static final int            ANIMATION_13_EYES_LOOK_AT_NOISE               = 13;
   /** squint outside */
   public static final int            ANIMATION_14_EYES_LOOK_AT_DIFFERENT_DIRECTION = 14;
   /** Animation specific : Make eyes dizzy */
   public static final int            ANIMATION_15_EYES_DIZZY                       = 15;
   /** Animation specific : Return to start position */
   public static final int            ANIMATION_16_EYES_START_POSITION              = 16;
   /** Main body node name */
   public static final String         BODY                                          = "Body";
   /** Default color for eyes */
   public static final int            DEFAULT_EYE_COLOR                             = 0xFF80DEEA;
   /** Default color for hair */
   public static final int            DEFAULT_HAIR_COLOR                            = 0xFF3E2723;
   /** Default color for skin */
   public static final int            DEFAULT_SKIN_COLOR                            = 0xFFDFAB9B;
   /** Dress node name */
   public static final String         DRESS                                         = "dress";
   /** Left eye node name */
   public static final String         EYE_LEFT                                      = "EyeLeft";
   /** Right eye node name */
   public static final String         EYE_RIGHT                                     = "EyeRight";
   /** Left feet node name */
   public static final String         FOOT_LEFT                                     = "FootLeft";
   /** Left feet node for rotate the feet node name */
   public static final String         FOOT_LEFT_ROTATION                            = "FootLeftRotation";
   /** Right feet node name */
   public static final String         FOOT_RIGHT                                    = "FootRight";
   /** Right feet for rotate the feet node name */
   public static final String         FOOT_RIGHT_ROTATION                           = "FootRightRotation";
   /** Hair node name */
   public static final String         HAIR                                          = "Hair";
   /** Left hand node name */
   public static final String         HAND_LEFT                                     = "HandLeft";
   /** Right hand node name */
   public static final String         HAND_RIGHT                                    = "HandRight";
   /** Left arm high node name */
   public static final String         HARM_LEFT_HIGH                                = "HarmLeftHigh";
   /** Left arm low node name */
   public static final String         HARM_LEFT_LOW                                 = "HarmLeftLow";
   /** Right arm high node name */
   public static final String         HARM_RIGHT_HIGH                               = "HarmRightHigh";
   /** Right arm low node name */
   public static final String         HARM_RIGHT_LOW                                = "HarmRightLow";
   /** Head node name */
   public static final String         HEAD                                          = "Head";
   /** Head node for rotate head name */
   public static final String         HEAD_ROTATION                                 = "HeadRotation";
   /** Left leg high node name */
   public static final String         LEG_LEFT_HIGH                                 = "LegLeftHigh";
   /** Left leg low node name */
   public static final String         LEG_LEFT_LOW                                  = "LegLeftLow";
   /** Right leg high node name */
   public static final String         LEG_RIGHT_HIGH                                = "LegRightHigh";
   /** Right leg low node name */
   public static final String         LEG_RIGHT_LOW                                 = "LegRightLow";
   /** Number of specific animations */
   public static final int            NUMBER_OF_SPECIFIC_ANIMATION                  = 17;

   /**
    * Add head to a given node
    * 
    * @param parent
    *           Parent where attach the head
    * @return Box for compute the rest of the body
    */
   static VirtualBox addHumanHeadTo(final Node parent)
   {
      VirtualBox virtualBox = new VirtualBox();

       final Object3D neutral = (Object3D) ResourcesGame3D.loadModel("humanFace.obj")
                                                          .getFirstNode("neutral_main");
      ResourcesGame3D.loadModel("humanFace.obj")
                     .removeChild(neutral);
      virtualBox = neutral.getBox();
      final float vy = (virtualBox.getMaxY() + virtualBox.getMinY()) / 2f;
      neutral.recenterObject();
      virtualBox = neutral.getBox();
      neutral.nodeName = CharacterHuman.HEAD;
      neutral.setPosition(0, vy, 0);

      final Object3D hair = (Object3D) ResourcesGame3D.loadModel("humanFace.obj")
                                                      .getFirstNode("hair_short");
      ResourcesGame3D.loadModel("humanFace.obj")
                     .removeChild(hair);
      hair.recenterObject();
      hair.scale(1.1f);
      hair.translate(0, 5f, 0f);
      hair.setTwoSidedState(TwoSidedState.FORCE_TWO_SIDE);
      hair.nodeName = CharacterHuman.HAIR;
      neutral.addChild(hair);

      final Object3D leftEye = new Sphere();
      leftEye.setPosition(5, -1, 9f);
      leftEye.setAngleX(0);
      leftEye.setAngleY(-80);
      leftEye.setAngleZ(0);
      leftEye.setScale(3);
      leftEye.limitAngleX(-35, 35);
      leftEye.limitAngleY(-130, -40);
      leftEye.limitAngleZ(0, 0);
      leftEye.nodeName = CharacterHuman.EYE_LEFT;
      neutral.addChild(leftEye);

      final Object3D rightEye = new Sphere();
      rightEye.setPosition(-5, -1, 9f);
      rightEye.setAngleX(0);
      rightEye.setAngleY(-100);
      rightEye.setAngleZ(0);
      rightEye.setScale(3);
      rightEye.limitAngleX(-35, 35);
      rightEye.limitAngleY(-130, -40);
      rightEye.limitAngleZ(0, 0);
      rightEye.nodeName = CharacterHuman.EYE_RIGHT;
      neutral.addChild(rightEye);

      final Node headRotation = new Node();
      headRotation.nodeName = CharacterHuman.HEAD_ROTATION;
      headRotation.addChild(neutral);
      headRotation.setPosition(0, -vy, 0);
      headRotation.limitAngleX(-90, 90);
      headRotation.limitAngleY(-90, 90);
      headRotation.limitAngleZ(-45, 45);

      parent.addChild(headRotation);
      return virtualBox;
   }

   /**
    * Create default unified material
    * 
    * @param name
    *           Material name
    * @param color
    *           Unify color
    * @return Created material
    */
   public static Material createDefaultMaterial(final String name, final int color)
   {
      final Material material = new Material(name);
      material.setColorEmissive(Color4f.BLACK);
      material.setColorDiffuse(new Color4f(color));
      return material;
   }

   /** Animation for : I don't know */
   private AnimationParallel     expressionIDontKnow;
   /** Animation for : say No */
   private AnimationPositionNode expressionNo;
   /** Animation for : Yahoo */
   private AnimationParallel     expressionYahoo;
   /** Animation for : Yeah */
   private AnimationParallel     expressionYeah;
   /** Animation for : say Yes */
   private AnimationPositionNode expressionYes;
   /** Left eye start position */
   private final PositionNode    eyeLeftPosition;
   /** Right eye start position */
   private final PositionNode    eyeRightPosition;
   /** Dizzy eyes animation */
   private AnimationParallel     eyesDizzy;
   /** Node for rotate left feet start position */
   private final PositionNode    footRotationLeftPosition;
   /** Node for rotate right feet start position */
   private final PositionNode    footRotationRightPosition;
   /** Node for left hand start position */
   private final PositionNode    handLeftPosition;
   /** Node for right hand start position */
   private final PositionNode    handRightPosition;
   /** Node for left high arm position */
   private final PositionNode    harmLeftHighPosition;
   /** Node for left low arm position */
   private final PositionNode    harmLeftLowPosition;
   /** Node for right high arm position */
   private final PositionNode    harmRightHighPosition;
   /** Node for right low arm position */
   private final PositionNode    harmRightLowPosition;
   /** Node for head position */
   private final PositionNode    headPosition;
   /** Node for left high leg position */
   private final PositionNode    legLeftHighPosition;
   /** Node for left low leg position */
   private final PositionNode    legLeftLowPosition;
   /** Node for right high leg position */
   private final PositionNode    legRightHighPosition;
   /** Node for right low leg position */
   private final PositionNode    legRightLowPosition;
   /** Character main node */
   private final Node            mainNode;
   /** Run animation */
   private AnimationParallel     run;
   /** Run legs only animation */
   private AnimationParallel     runLegs;
   /** Walk fast animation */
   private AnimationParallel     walkFast;
   /** Walk fast legs only animation */
   private AnimationParallel     walkFastLegs;
   /** Walk animation */
   private AnimationParallel     walkNormal;
   /** Walk legs only animation */
   private AnimationParallel     walkNormalLegs;
   /** Walk slow animation */
   private AnimationParallel     walkSlow;
   /** Walk slow legs only animation */
   private AnimationParallel     walkSlowLegs;
   /** Human character ID */
   protected final int           id;

   /**
    * Create a new instance of CharacterHuman
    * 
    * @param mainNode
    *           Main node
    */
   public CharacterHuman(final Node mainNode)
   {
      if(mainNode == null)
      {
         throw new NullPointerException("mainNode musn't be null");
      }

      this.id = CharacterHuman.NEXT_ID.getAndIncrement();
      this.mainNode = mainNode;

      NodeWithMaterial nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(CharacterHuman.HEAD);
      nodeWithMaterial.setMaterial(CharacterHuman.createDefaultMaterial(CharacterHuman.HEAD + this.id, CharacterHuman.DEFAULT_SKIN_COLOR));

      nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(CharacterHuman.HAIR);
      nodeWithMaterial.setMaterial(CharacterHuman.createDefaultMaterial(CharacterHuman.HAIR + this.id, CharacterHuman.DEFAULT_HAIR_COLOR));

      nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(CharacterHuman.EYE_LEFT);
      nodeWithMaterial.setMaterial(CharacterHuman.createDefaultMaterial(CharacterHuman.EYE_LEFT + this.id, CharacterHuman.DEFAULT_EYE_COLOR));

      nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(CharacterHuman.EYE_RIGHT);
      nodeWithMaterial.setMaterial(CharacterHuman.createDefaultMaterial(CharacterHuman.EYE_RIGHT + this.id, CharacterHuman.DEFAULT_EYE_COLOR));

      nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(CharacterHuman.BODY);
      nodeWithMaterial.setMaterial(CharacterHuman.createDefaultMaterial(CharacterHuman.BODY + this.id, CharacterHuman.DEFAULT_SKIN_COLOR));

      nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(CharacterHuman.HARM_RIGHT_HIGH);
      nodeWithMaterial.setMaterial(CharacterHuman.createDefaultMaterial(CharacterHuman.HARM_RIGHT_HIGH + this.id, CharacterHuman.DEFAULT_SKIN_COLOR));

      nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(CharacterHuman.HARM_RIGHT_LOW);
      nodeWithMaterial.setMaterial(CharacterHuman.createDefaultMaterial(CharacterHuman.HARM_RIGHT_LOW + this.id, CharacterHuman.DEFAULT_SKIN_COLOR));

      nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(CharacterHuman.HAND_RIGHT);
      nodeWithMaterial.setMaterial(CharacterHuman.createDefaultMaterial(CharacterHuman.HAND_RIGHT + this.id, CharacterHuman.DEFAULT_SKIN_COLOR));

      nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(CharacterHuman.HARM_LEFT_HIGH);
      nodeWithMaterial.setMaterial(CharacterHuman.createDefaultMaterial(CharacterHuman.HARM_LEFT_HIGH + this.id, CharacterHuman.DEFAULT_SKIN_COLOR));

      nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(CharacterHuman.HARM_LEFT_LOW);
      nodeWithMaterial.setMaterial(CharacterHuman.createDefaultMaterial(CharacterHuman.HARM_LEFT_LOW + this.id, CharacterHuman.DEFAULT_SKIN_COLOR));

      nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(CharacterHuman.HAND_LEFT);
      nodeWithMaterial.setMaterial(CharacterHuman.createDefaultMaterial(CharacterHuman.HAND_LEFT + this.id, CharacterHuman.DEFAULT_SKIN_COLOR));

      nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(CharacterHuman.LEG_RIGHT_HIGH);
      nodeWithMaterial.setMaterial(CharacterHuman.createDefaultMaterial(CharacterHuman.LEG_RIGHT_HIGH + this.id, CharacterHuman.DEFAULT_SKIN_COLOR));

      nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(CharacterHuman.LEG_RIGHT_LOW);
      nodeWithMaterial.setMaterial(CharacterHuman.createDefaultMaterial(CharacterHuman.LEG_RIGHT_LOW + this.id, CharacterHuman.DEFAULT_SKIN_COLOR));

      nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(CharacterHuman.FOOT_RIGHT);
      nodeWithMaterial.setMaterial(CharacterHuman.createDefaultMaterial(CharacterHuman.FOOT_RIGHT + this.id, CharacterHuman.DEFAULT_SKIN_COLOR));

      nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(CharacterHuman.LEG_LEFT_HIGH);
      nodeWithMaterial.setMaterial(CharacterHuman.createDefaultMaterial(CharacterHuman.LEG_LEFT_HIGH + this.id, CharacterHuman.DEFAULT_SKIN_COLOR));

      nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(CharacterHuman.LEG_LEFT_LOW);
      nodeWithMaterial.setMaterial(CharacterHuman.createDefaultMaterial(CharacterHuman.LEG_LEFT_LOW + this.id, CharacterHuman.DEFAULT_SKIN_COLOR));

      nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(CharacterHuman.FOOT_LEFT);
      nodeWithMaterial.setMaterial(CharacterHuman.createDefaultMaterial(CharacterHuman.FOOT_LEFT + this.id, CharacterHuman.DEFAULT_SKIN_COLOR));

      this.headPosition = new PositionNode(mainNode.getFirstNode(CharacterHuman.HEAD_ROTATION));
      this.eyeLeftPosition = new PositionNode(mainNode.getFirstNode(CharacterHuman.EYE_LEFT));
      this.eyeRightPosition = new PositionNode(mainNode.getFirstNode(CharacterHuman.EYE_RIGHT));
      this.harmRightHighPosition = new PositionNode(mainNode.getFirstNode(CharacterHuman.HARM_RIGHT_HIGH));
      this.harmRightLowPosition = new PositionNode(mainNode.getFirstNode(CharacterHuman.HARM_RIGHT_LOW));
      this.handRightPosition = new PositionNode(mainNode.getFirstNode(CharacterHuman.HAND_RIGHT));
      this.harmLeftHighPosition = new PositionNode(mainNode.getFirstNode(CharacterHuman.HARM_LEFT_HIGH));
      this.harmLeftLowPosition = new PositionNode(mainNode.getFirstNode(CharacterHuman.HARM_LEFT_LOW));
      this.handLeftPosition = new PositionNode(mainNode.getFirstNode(CharacterHuman.HAND_LEFT));
      this.legRightHighPosition = new PositionNode(mainNode.getFirstNode(CharacterHuman.LEG_RIGHT_HIGH));
      this.legRightLowPosition = new PositionNode(mainNode.getFirstNode(CharacterHuman.LEG_RIGHT_LOW));
      this.footRotationRightPosition = new PositionNode(mainNode.getFirstNode(CharacterHuman.FOOT_RIGHT_ROTATION));
      this.legLeftHighPosition = new PositionNode(mainNode.getFirstNode(CharacterHuman.LEG_LEFT_HIGH));
      this.legLeftLowPosition = new PositionNode(mainNode.getFirstNode(CharacterHuman.LEG_LEFT_LOW));
      this.footRotationLeftPosition = new PositionNode(mainNode.getFirstNode(CharacterHuman.FOOT_LEFT_ROTATION));

      // Pre-load animations (To be sure they always start at good position)
      this.obtainWalkSlowAnimation();
      this.obtainWalkNormalAnimation();
      this.obtainWalkFastAnimation();
      this.obtainRunAnimation();
      this.obtainExpressionYes();
      this.obtainExpressionNo();
      this.obtainExpressionIDontKnow();
      this.obtainExpressionYeah();
      this.obtainExpressionYahoo();
      this.obtainEyesDizzy();

      //
      this.setHairColor(CharacterHuman.DEFAULT_HAIR_COLOR);

      final NodeWithMaterial dress = (NodeWithMaterial) this.getCharacterNode().getFirstNode(CharacterHuman.DRESS);
      final Material material = CharacterHuman.createDefaultMaterial(CharacterHuman.DRESS + this.id, 0xFF37474F);
      dress.setMaterial(material);
   }

   /**
    * Create an animation for put a body member to start position
    * 
    * @param nodeName
    *           Body part name
    * @param positionNode
    *           Position to go
    * @param frame
    *           Number of frame
    * @return Created animation
    */
   private Animation createResetAnimation(final String nodeName, final PositionNode positionNode, final int frame)
   {
      final AnimationPositionNode animationPositionNode = new AnimationPositionNode(this.mainNode.getFirstNode(nodeName));
      animationPositionNode.addFrame(frame, positionNode);
      return animationPositionNode;
   }

   /**
    * Obtain body part name
    * 
    * @param bodyPart
    *           Body part
    * @return Body part name
    */
   private String getName(final BodyPart bodyPart)
   {
      switch(bodyPart)
      {
         case BODY:
            return CharacterHuman.BODY;
         case DRESS:
            return CharacterHuman.DRESS;
         case EYE_LEFT:
            return CharacterHuman.EYE_LEFT;
         case EYE_RIGHT:
            return CharacterHuman.EYE_RIGHT;
         case FEET_LEFT:
            return CharacterHuman.FOOT_LEFT;
         case FEET_RIGHT:
            return CharacterHuman.FOOT_RIGHT;
         case HAIR:
            return CharacterHuman.HAIR;
         case HAND_LEFT:
            return CharacterHuman.HAND_LEFT;
         case HAND_RIGHT:
            return CharacterHuman.HAND_RIGHT;
         case HARM_HIGH_LEFT:
            return CharacterHuman.HARM_LEFT_HIGH;
         case HARM_HIGH_RIGHT:
            return CharacterHuman.HARM_RIGHT_HIGH;
         case HARM_LOW_LEFT:
            return CharacterHuman.HARM_LEFT_LOW;
         case HARM_LOW_RIGHT:
            return CharacterHuman.HARM_RIGHT_LOW;
         case HEAD:
            return CharacterHuman.HEAD;
         case LEG_HIGH_LEFT:
            return CharacterHuman.LEG_LEFT_HIGH;
         case LEG_HIGH_RIGHT:
            return CharacterHuman.LEG_RIGHT_HIGH;
         case LEG_LOW_LEFT:
            return CharacterHuman.LEG_LEFT_LOW;
         case LEG_LOW_RIGHT:
            return CharacterHuman.LEG_RIGHT_LOW;
      }

      return null;
   }

   /**
    * Obtain or create animation : I don't know
    * 
    * @return Animation : I don't know
    */
   private Animation obtainExpressionIDontKnow()
   {
      if(this.expressionIDontKnow == null)
      {
         this.expressionIDontKnow = new AnimationParallel();

         Node node = this.mainNode.getFirstNode(CharacterHuman.HARM_RIGHT_HIGH);
         PositionNode startPosition = new PositionNode(node);
         AnimationPositionNode animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(CharacterHuman.TIME_EXPRESSION * 4, startPosition);
         this.expressionIDontKnow.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.HARM_RIGHT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(CharacterHuman.TIME_EXPRESSION * 4, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX, startPosition.angleY, startPosition.angleZ - 90, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.expressionIDontKnow.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.HARM_LEFT_HIGH);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(CharacterHuman.TIME_EXPRESSION * 4, startPosition);
         this.expressionIDontKnow.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.HARM_LEFT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(CharacterHuman.TIME_EXPRESSION * 4, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX, startPosition.angleY, startPosition.angleZ + 90, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.expressionIDontKnow.addAnimation(animationPositionNode);
      }

      return this.expressionIDontKnow;
   }

   /**
    * Obtain or create animation : No
    * 
    * @return Animation : No
    */
   private Animation obtainExpressionNo()
   {
      if(this.expressionNo == null)
      {
         final Node node = this.mainNode.getFirstNode(CharacterHuman.HEAD_ROTATION);
         final PositionNode startPosition = new PositionNode(node);
         this.expressionNo = new AnimationPositionNode(node);
         this.expressionNo.addFrame(CharacterHuman.TIME_EXPRESSION / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX, startPosition.angleY - 30, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.expressionNo.addFrame((3 * CharacterHuman.TIME_EXPRESSION) / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX, startPosition.angleY + 30, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.expressionNo.addFrame((5 * CharacterHuman.TIME_EXPRESSION) / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX, startPosition.angleY - 30, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.expressionNo.addFrame((7 * CharacterHuman.TIME_EXPRESSION) / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX, startPosition.angleY + 30, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.expressionNo.addFrame((9 * CharacterHuman.TIME_EXPRESSION) / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX, startPosition.angleY - 30, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.expressionNo.addFrame((11 * CharacterHuman.TIME_EXPRESSION) / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX, startPosition.angleY + 30, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.expressionNo.addFrame(6 * CharacterHuman.TIME_EXPRESSION, startPosition);
      }

      return this.expressionNo;
   }

   /**
    * Obtain or create animation : Yahoo
    * 
    * @return Animation : Yahoo
    */
   private Animation obtainExpressionYahoo()
   {
      if(this.expressionYahoo == null)
      {
         this.expressionYahoo = new AnimationParallel();

         Node node = this.mainNode.getFirstNode(CharacterHuman.HARM_RIGHT_HIGH);
         PositionNode startPosition = new PositionNode(node);
         AnimationPositionNode animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(CharacterHuman.TIME_EXPRESSION, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 45, startPosition.angleY + 90, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame((5 * CharacterHuman.TIME_EXPRESSION) / 4, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 180, startPosition.angleY + 90, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.expressionYahoo.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.HARM_RIGHT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(CharacterHuman.TIME_EXPRESSION, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX, startPosition.angleY, startPosition.angleZ - 135, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame((5 * CharacterHuman.TIME_EXPRESSION) / 4, startPosition);
         this.expressionYahoo.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.HARM_LEFT_HIGH);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(CharacterHuman.TIME_EXPRESSION, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 45, startPosition.angleY - 90, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame((5 * CharacterHuman.TIME_EXPRESSION) / 4, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 180, startPosition.angleY - 90, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.expressionYahoo.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.HARM_LEFT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(CharacterHuman.TIME_EXPRESSION, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX, startPosition.angleY, startPosition.angleZ + 135, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame((5 * CharacterHuman.TIME_EXPRESSION) / 4, startPosition);
         this.expressionYahoo.addAnimation(animationPositionNode);
      }

      return this.expressionYahoo;
   }

   /**
    * Obtain or create animation : Yeah
    * 
    * @return Animation : Yeah
    */
   private Animation obtainExpressionYeah()
   {
      if(this.expressionYeah == null)
      {
         this.expressionYeah = new AnimationParallel();

         Node node = this.mainNode.getFirstNode(CharacterHuman.HARM_RIGHT_HIGH);
         PositionNode startPosition = new PositionNode(node);
         AnimationPositionNode animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(CharacterHuman.TIME_EXPRESSION * 3, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 90, startPosition.angleY + 90, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame((7 * CharacterHuman.TIME_EXPRESSION) / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 45, startPosition.angleY + 90, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.expressionYeah.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.HARM_RIGHT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(CharacterHuman.TIME_EXPRESSION * 3, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX, startPosition.angleY, startPosition.angleZ - 90, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame((7 * CharacterHuman.TIME_EXPRESSION) / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX, startPosition.angleY, startPosition.angleZ - 135, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.expressionYeah.addAnimation(animationPositionNode);
      }

      return this.expressionYeah;
   }

   /**
    * Obtain or create animation : Yes
    * 
    * @return Animation : Yes
    */
   private Animation obtainExpressionYes()
   {
      if(this.expressionYes == null)
      {
         final Node node = this.mainNode.getFirstNode(CharacterHuman.HEAD_ROTATION);
         final PositionNode startPosition = new PositionNode(node);
         this.expressionYes = new AnimationPositionNode(node);
         this.expressionYes.addFrame(0, startPosition);
         this.expressionYes.addFrame(CharacterHuman.TIME_EXPRESSION / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 30, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.expressionYes.addFrame((3 * CharacterHuman.TIME_EXPRESSION) / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 30, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.expressionYes.addFrame((5 * CharacterHuman.TIME_EXPRESSION) / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 30, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.expressionYes.addFrame((7 * CharacterHuman.TIME_EXPRESSION) / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 30, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.expressionYes.addFrame((9 * CharacterHuman.TIME_EXPRESSION) / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 30, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.expressionYes.addFrame((11 * CharacterHuman.TIME_EXPRESSION) / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 30, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.expressionYes.addFrame(6 * CharacterHuman.TIME_EXPRESSION, startPosition);
      }

      return this.expressionYes;
   }

   /**
    * Obtain or create animation : Dizzy eyes
    * 
    * @return Animation : Dizzy eyes
    */
   private Animation obtainEyesDizzy()
   {
      if(this.eyesDizzy != null)
      {
         return this.eyesDizzy;
      }

      this.eyesDizzy = new AnimationParallel();

      Node node = this.mainNode.getFirstNode(CharacterHuman.EYE_LEFT);
      PositionNode startPositionNode = new PositionNode(node);
      AnimationPositionNode animationPositionNode = new AnimationPositionNode(node);
      int time = CharacterHuman.TIME_EXPRESSION / 4;
      int more = time;

      for(int i = 0; i < 9; i++)
      {
         animationPositionNode.addFrame(time, new PositionNode(startPositionNode.x, startPositionNode.y, startPositionNode.z, 0, -130,
               startPositionNode.angleZ, startPositionNode.scaleX, startPositionNode.scaleY, startPositionNode.scaleZ));
         time += more;
         animationPositionNode.addFrame(time, new PositionNode(startPositionNode.x, startPositionNode.y, startPositionNode.z, -35, -80,
               startPositionNode.angleZ, startPositionNode.scaleX, startPositionNode.scaleY, startPositionNode.scaleZ));
         time += more;
         animationPositionNode.addFrame(time, new PositionNode(startPositionNode.x, startPositionNode.y, startPositionNode.z, 0, -40, startPositionNode.angleZ,
               startPositionNode.scaleX, startPositionNode.scaleY, startPositionNode.scaleZ));
         time += more;
         animationPositionNode.addFrame(time, new PositionNode(startPositionNode.x, startPositionNode.y, startPositionNode.z, 35, -80,
               startPositionNode.angleZ, startPositionNode.scaleX, startPositionNode.scaleY, startPositionNode.scaleZ));
         time += more;
      }

      this.eyesDizzy.addAnimation(animationPositionNode);

      node = this.mainNode.getFirstNode(CharacterHuman.EYE_RIGHT);
      startPositionNode = new PositionNode(node);
      animationPositionNode = new AnimationPositionNode(node);
      time = CharacterHuman.TIME_EXPRESSION / 4;
      more = time;

      for(int i = 0; i < 9; i++)
      {
         animationPositionNode.addFrame(time, new PositionNode(startPositionNode.x, startPositionNode.y, startPositionNode.z, 0, -130,
               startPositionNode.angleZ, startPositionNode.scaleX, startPositionNode.scaleY, startPositionNode.scaleZ));
         time += more;
         animationPositionNode.addFrame(time, new PositionNode(startPositionNode.x, startPositionNode.y, startPositionNode.z, -35, -100,
               startPositionNode.angleZ, startPositionNode.scaleX, startPositionNode.scaleY, startPositionNode.scaleZ));
         time += more;
         animationPositionNode.addFrame(time, new PositionNode(startPositionNode.x, startPositionNode.y, startPositionNode.z, 0, -40, startPositionNode.angleZ,
               startPositionNode.scaleX, startPositionNode.scaleY, startPositionNode.scaleZ));
         time += more;
         animationPositionNode.addFrame(time, new PositionNode(startPositionNode.x, startPositionNode.y, startPositionNode.z, 35, -100,
               startPositionNode.angleZ, startPositionNode.scaleX, startPositionNode.scaleY, startPositionNode.scaleZ));
         time += more;
      }

      this.eyesDizzy.addAnimation(animationPositionNode);

      return this.eyesDizzy;
   }

   /**
    * Obtain or create animation : Run leg only
    * 
    * @return Animation : Run leg only
    */
   private Animation obtainRunLegsAnimation()
   {
      if(this.runLegs == null)
      {
         this.runLegs = new AnimationParallel();

         // Right

         Node node = this.mainNode.getFirstNode(CharacterHuman.LEG_RIGHT_HIGH);
         PositionNode startPosition = new PositionNode(node);
         AnimationPositionNode animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX + 45, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_RUN / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 90, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_RUN, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX + 45,
               startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.runLegs.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.LEG_RIGHT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, startPosition);
         animationPositionNode.addFrame(CharacterHuman.TIME_RUN / 5, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 90, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_RUN / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 90, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame((3 * CharacterHuman.TIME_RUN) / 4, startPosition);
         animationPositionNode.addFrame(CharacterHuman.TIME_RUN, startPosition);
         this.runLegs.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.FOOT_RIGHT_ROTATION);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX - 45, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_RUN / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 90, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_RUN, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX - 45,
               startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.runLegs.addAnimation(animationPositionNode);

         //

         // Left

         node = this.mainNode.getFirstNode(CharacterHuman.LEG_LEFT_HIGH);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX - 90, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_RUN / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 45, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_RUN, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX - 90,
               startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.runLegs.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.LEG_LEFT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX + 90, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_RUN / 4, startPosition);
         animationPositionNode.addFrame(CharacterHuman.TIME_RUN / 2, startPosition);
         animationPositionNode.addFrame((3 * CharacterHuman.TIME_RUN) / 4, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 90, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_RUN, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX + 90,
               startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.runLegs.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.FOOT_LEFT_ROTATION);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX + 90, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_RUN / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 45, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_RUN, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX + 90,
               startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.runLegs.addAnimation(animationPositionNode);
      }

      return this.runLegs;
   }

   /**
    * Obtain or create animation : Walk fast, legs only
    * 
    * @return Animation : Walk fast, legs only
    */
   private Animation obtainWalkFastLegsAnimation()
   {
      if(this.walkFastLegs == null)
      {
         this.walkFastLegs = new AnimationParallel();

         // Right

         Node node = this.mainNode.getFirstNode(CharacterHuman.LEG_RIGHT_HIGH);
         PositionNode startPosition = new PositionNode(node);
         AnimationPositionNode animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX + 45, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 45, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 45, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.walkFastLegs.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.LEG_RIGHT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, startPosition);
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 5, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 35, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, startPosition);
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, startPosition);
         this.walkFastLegs.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.FOOT_RIGHT_ROTATION);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX - 45, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 45, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 45, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.walkFastLegs.addAnimation(animationPositionNode);

         //

         // Left

         node = this.mainNode.getFirstNode(CharacterHuman.LEG_LEFT_HIGH);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX - 45, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 45, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 45, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.walkFastLegs.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.LEG_LEFT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, startPosition);
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, startPosition);
         animationPositionNode.addFrame((7 * CharacterHuman.TIME_WALK) / 10, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 35, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, startPosition);
         this.walkFastLegs.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.FOOT_LEFT_ROTATION);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX + 45, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 45, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 45, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.walkFastLegs.addAnimation(animationPositionNode);
      }

      return this.walkFastLegs;
   }

   /**
    * Obtain or create animation : Walk, legs only
    * 
    * @return Animation : Walk, legs only
    */
   private Animation obtainWalkNormalLegsAnimation()
   {
      if(this.walkNormalLegs == null)
      {
         this.walkNormalLegs = new AnimationParallel();

         // Right

         Node node = this.mainNode.getFirstNode(CharacterHuman.LEG_RIGHT_HIGH);
         PositionNode startPosition = new PositionNode(node);
         AnimationPositionNode animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX + 25, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 25, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 25, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.walkNormalLegs.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.LEG_RIGHT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, startPosition);
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 5, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 30, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, startPosition);
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, startPosition);
         this.walkNormalLegs.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.FOOT_RIGHT_ROTATION);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX - 25, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 25, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 25, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.walkNormalLegs.addAnimation(animationPositionNode);

         //

         // Left

         node = this.mainNode.getFirstNode(CharacterHuman.LEG_LEFT_HIGH);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX - 25, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 25, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 25, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.walkNormalLegs.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.LEG_LEFT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, startPosition);
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, startPosition);
         animationPositionNode.addFrame((7 * CharacterHuman.TIME_WALK) / 10, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 30, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, startPosition);
         this.walkNormalLegs.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.FOOT_LEFT_ROTATION);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX + 25, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 25, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 25, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.walkNormalLegs.addAnimation(animationPositionNode);
      }

      return this.walkNormalLegs;
   }

   /**
    * Obtain or create animation : Walk slow, legs only
    * 
    * @return Animation : Walk slow, legs only
    */
   private Animation obtainWalkSlowLegsAnimation()
   {
      if(this.walkSlowLegs == null)
      {
         this.walkSlowLegs = new AnimationParallel();

         // Right

         Node node = this.mainNode.getFirstNode(CharacterHuman.LEG_RIGHT_HIGH);
         PositionNode startPosition = new PositionNode(node);
         AnimationPositionNode animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX + 12, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 12, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 12, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.walkSlowLegs.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.LEG_RIGHT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, startPosition);
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 5, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 25, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, startPosition);
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, startPosition);
         this.walkSlowLegs.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.FOOT_RIGHT_ROTATION);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX - 12, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 12, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 12, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.walkSlowLegs.addAnimation(animationPositionNode);

         //

         // Left

         node = this.mainNode.getFirstNode(CharacterHuman.LEG_LEFT_HIGH);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX - 12, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 12, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 12, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.walkSlowLegs.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.LEG_LEFT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, startPosition);
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, startPosition);
         animationPositionNode.addFrame((7 * CharacterHuman.TIME_WALK) / 10, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 25, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, startPosition);
         this.walkSlowLegs.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.FOOT_LEFT_ROTATION);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX + 12, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 12, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 12, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.walkSlowLegs.addAnimation(animationPositionNode);
      }

      return this.walkSlowLegs;
   }

   /**
    * Change eye texture
    * 
    * @param eyeName
    *           Eye node name
    * @param eyesTexture
    *           New eye texture
    */
   private void setTextureForEye(final String eyeName, final EyesTexture eyesTexture)
   {
      final NodeWithMaterial nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(eyeName);
      final Material material = nodeWithMaterial.getMaterial();
      material.setColorDiffuse(Color4f.WHITE);
      material.setTextureDiffuse(eyesTexture.getTexture());
   }

   /**
    * Main node for manipulate human character <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @return Main node for manipulate human character
    * @see jhelp.game3d.models.Character#getCharacterNode()
    */
   @Override
   public Node getCharacterNode()
   {
      return this.mainNode;
   }

   /**
    * Obtain color on body part
    * 
    * @param bodyPart
    *           Bordy part
    * @return Color on body part
    */
   public int getColor(final BodyPart bodyPart)
   {
      int color = 0;
      final String name = this.getName(bodyPart);

      if(name != null)
      {
         final NodeWithMaterial nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(name);

         if(nodeWithMaterial != null)
         {
            final Material material = nodeWithMaterial.getMaterial();
            color = material.getColorDiffuse().getARGB();
         }
      }

      return color;
   }

   /**
    * Current dress scale
    * 
    * @return Current dress scale
    */
   public float getDressScale()
   {
      return this.getCharacterNode().getFirstNode(CharacterHuman.DRESS).getScaleY();
   }

   /**
    * Character human ID
    * 
    * @return Character human ID
    */
   public int getID()
   {
      return this.id;
   }

   /**
    * Indicates if dress is visible
    * 
    * @return {@code true} if dress is visible
    */
   public boolean isDressVisible()
   {
      return this.getCharacterNode().getFirstNode(CharacterHuman.DRESS).isVisible();
   }

   /**
    * Number of specific animations <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @return Number of specific animations
    * @see jhelp.game3d.models.Character#numberOfSpecificAnimation()
    */
   @Override
   public int numberOfSpecificAnimation()
   {
      return CharacterHuman.NUMBER_OF_SPECIFIC_ANIMATION;
   }

   /**
    * Create animation make eyes to squint.<br>
    * Left eye go to look completely at left and right eye completely at right
    * 
    * @param frame
    *           Number of frame for animation
    * @return Animation for squint
    */
   public Animation obtainEyesLookAtDifferentDirection(final int frame)
   {
      final AnimationParallel animationParallel = new AnimationParallel();

      Node node = this.mainNode.getFirstNode(CharacterHuman.EYE_LEFT);
      PositionNode startPosition = this.eyeLeftPosition;
      AnimationPositionNode animationPositionNode = new AnimationPositionNode(node);
      animationPositionNode.addFrame(frame, new PositionNode(startPosition.x, startPosition.y, startPosition.z, 0, -40, startPosition.angleZ,
            startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
      animationParallel.addAnimation(animationPositionNode);

      node = this.mainNode.getFirstNode(CharacterHuman.EYE_RIGHT);
      startPosition = this.eyeRightPosition;
      animationPositionNode = new AnimationPositionNode(node);
      animationPositionNode.addFrame(frame, new PositionNode(startPosition.x, startPosition.y, startPosition.z, 0, -130, startPosition.angleZ,
            startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
      animationParallel.addAnimation(animationPositionNode);

      return animationParallel;
   }

   /**
    * Create animation for eyes look at down
    * 
    * @param frame
    *           Number of frame for animation
    * @return Animation to look down
    */
   public Animation obtainEyesLookAtDown(final int frame)
   {
      final AnimationParallel animationParallel = new AnimationParallel();

      Node node = this.mainNode.getFirstNode(CharacterHuman.EYE_LEFT);
      PositionNode startPosition = this.eyeLeftPosition;
      AnimationPositionNode animationPositionNode = new AnimationPositionNode(node);
      animationPositionNode.addFrame(frame, new PositionNode(startPosition.x, startPosition.y, startPosition.z, 35, -80, startPosition.angleZ,
            startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
      animationParallel.addAnimation(animationPositionNode);

      node = this.mainNode.getFirstNode(CharacterHuman.EYE_RIGHT);
      startPosition = this.eyeRightPosition;
      animationPositionNode = new AnimationPositionNode(node);
      animationPositionNode.addFrame(frame, new PositionNode(startPosition.x, startPosition.y, startPosition.z, 35, -100, startPosition.angleZ,
            startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
      animationParallel.addAnimation(animationPositionNode);

      return animationParallel;
   }

   /**
    * Create animation for eyes look at left
    * 
    * @param frame
    *           Number of frame for animation
    * @return Animation to look left
    */
   public Animation obtainEyesLookAtLeft(final int frame)
   {
      final AnimationParallel animationParallel = new AnimationParallel();

      Node node = this.mainNode.getFirstNode(CharacterHuman.EYE_LEFT);
      PositionNode startPosition = this.eyeLeftPosition;
      AnimationPositionNode animationPositionNode = new AnimationPositionNode(node);
      animationPositionNode.addFrame(frame, new PositionNode(startPosition.x, startPosition.y, startPosition.z, 0, -40, startPosition.angleZ,
            startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
      animationParallel.addAnimation(animationPositionNode);

      node = this.mainNode.getFirstNode(CharacterHuman.EYE_RIGHT);
      startPosition = this.eyeRightPosition;
      animationPositionNode = new AnimationPositionNode(node);
      animationPositionNode.addFrame(frame, new PositionNode(startPosition.x, startPosition.y, startPosition.z, 0, -40, startPosition.angleZ,
            startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
      animationParallel.addAnimation(animationPositionNode);

      return animationParallel;
   }

   /**
    * Create animation for eyes squint on looking the noise
    * 
    * @param frame
    *           Number of frame for animation
    * @return Animation to squint to noise
    */
   public Animation obtainEyesLookAtNoise(final int frame)
   {
      final AnimationParallel animationParallel = new AnimationParallel();

      Node node = this.mainNode.getFirstNode(CharacterHuman.EYE_LEFT);
      PositionNode startPosition = this.eyeLeftPosition;
      AnimationPositionNode animationPositionNode = new AnimationPositionNode(node);
      animationPositionNode.addFrame(frame, new PositionNode(startPosition.x, startPosition.y, startPosition.z, 0, -130, startPosition.angleZ,
            startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
      animationParallel.addAnimation(animationPositionNode);

      node = this.mainNode.getFirstNode(CharacterHuman.EYE_RIGHT);
      startPosition = this.eyeRightPosition;
      animationPositionNode = new AnimationPositionNode(node);
      animationPositionNode.addFrame(frame, new PositionNode(startPosition.x, startPosition.y, startPosition.z, 0, -40, startPosition.angleZ,
            startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
      animationParallel.addAnimation(animationPositionNode);

      return animationParallel;
   }

   /**
    * Create animation for eyes look at right
    * 
    * @param frame
    *           Number of frame for animation
    * @return Animation to look right
    */
   public Animation obtainEyesLookAtRight(final int frame)
   {
      final AnimationParallel animationParallel = new AnimationParallel();

      Node node = this.mainNode.getFirstNode(CharacterHuman.EYE_LEFT);
      PositionNode startPosition = this.eyeLeftPosition;
      AnimationPositionNode animationPositionNode = new AnimationPositionNode(node);
      animationPositionNode.addFrame(frame, new PositionNode(startPosition.x, startPosition.y, startPosition.z, 0, -130, startPosition.angleZ,
            startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
      animationParallel.addAnimation(animationPositionNode);

      node = this.mainNode.getFirstNode(CharacterHuman.EYE_RIGHT);
      startPosition = this.eyeRightPosition;
      animationPositionNode = new AnimationPositionNode(node);
      animationPositionNode.addFrame(frame, new PositionNode(startPosition.x, startPosition.y, startPosition.z, 0, -130, startPosition.angleZ,
            startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
      animationParallel.addAnimation(animationPositionNode);

      return animationParallel;
   }

   /**
    * Create animation for eyes look at start position
    * 
    * @param frame
    *           Number of frame for animation
    * @return Animation to look at start position
    */
   public Animation obtainEyesLookAtStartPosition(final int frame)
   {
      final AnimationParallel animationParallel = new AnimationParallel();

      Node node = this.mainNode.getFirstNode(CharacterHuman.EYE_LEFT);
      PositionNode startPosition = this.eyeLeftPosition;
      AnimationPositionNode animationPositionNode = new AnimationPositionNode(node);
      animationPositionNode.addFrame(frame, new PositionNode(startPosition));
      animationParallel.addAnimation(animationPositionNode);

      node = this.mainNode.getFirstNode(CharacterHuman.EYE_RIGHT);
      startPosition = this.eyeRightPosition;
      animationPositionNode = new AnimationPositionNode(node);
      animationPositionNode.addFrame(frame, new PositionNode(startPosition));
      animationParallel.addAnimation(animationPositionNode);

      return animationParallel;
   }

   /**
    * Create animation for eyes look at up
    * 
    * @param frame
    *           Number of frame for animation
    * @return Animation to look up
    */
   public Animation obtainEyesLookAtUp(final int frame)
   {
      final AnimationParallel animationParallel = new AnimationParallel();

      Node node = this.mainNode.getFirstNode(CharacterHuman.EYE_LEFT);
      PositionNode startPosition = this.eyeLeftPosition;
      AnimationPositionNode animationPositionNode = new AnimationPositionNode(node);
      animationPositionNode.addFrame(frame, new PositionNode(startPosition.x, startPosition.y, startPosition.z, -35, -80, startPosition.angleZ,
            startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
      animationParallel.addAnimation(animationPositionNode);

      node = this.mainNode.getFirstNode(CharacterHuman.EYE_RIGHT);
      startPosition = this.eyeRightPosition;
      animationPositionNode = new AnimationPositionNode(node);
      animationPositionNode.addFrame(frame, new PositionNode(startPosition.x, startPosition.y, startPosition.z, -35, -100, startPosition.angleZ,
            startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
      animationParallel.addAnimation(animationPositionNode);

      return animationParallel;
   }

   /**
    * Obtain material associate to a body part
    * 
    * @param bodyPart
    *           Body part
    * @return Associated material
    */
   public Material obtainMaterial(final BodyPart bodyPart)
   {
      final String name = this.getName(bodyPart);

      if(name == null)
      {
         return null;
      }

      final NodeWithMaterial nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(name);

      if(nodeWithMaterial == null)
      {
         return null;
      }

      return nodeWithMaterial.getMaterial();
   }

   /**
    * Obtain the node for body part
    * 
    * @param bodyPart
    *           Body part
    * @return Associated node
    */
   public Node obtainNode(final BodyPart bodyPart)
   {
      final String name = this.getName(bodyPart);

      if(name == null)
      {
         return null;
      }

      return this.mainNode.getFirstNode(name);
   }

   /**
    * Animation for put all body parts to their start position <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param frame
    *           Number of frame of animation
    * @return Created animation
    * @see jhelp.game3d.models.Character#obtainPutAtStartPositionAnimation(int)
    */
   @Override
   public Animation obtainPutAtStartPositionAnimation(final int frame)
   {
      final AnimationParallel animationParallel = new AnimationParallel();
      animationParallel.addAnimation(this.createResetAnimation(CharacterHuman.HEAD_ROTATION, this.headPosition, frame));
      animationParallel.addAnimation(this.createResetAnimation(CharacterHuman.EYE_LEFT, this.eyeLeftPosition, frame));
      animationParallel.addAnimation(this.createResetAnimation(CharacterHuman.EYE_RIGHT, this.eyeRightPosition, frame));
      animationParallel.addAnimation(this.createResetAnimation(CharacterHuman.HARM_RIGHT_HIGH, this.harmRightHighPosition, frame));
      animationParallel.addAnimation(this.createResetAnimation(CharacterHuman.HARM_RIGHT_LOW, this.harmRightLowPosition, frame));
      animationParallel.addAnimation(this.createResetAnimation(CharacterHuman.HAND_RIGHT, this.handRightPosition, frame));
      animationParallel.addAnimation(this.createResetAnimation(CharacterHuman.HARM_LEFT_HIGH, this.harmLeftHighPosition, frame));
      animationParallel.addAnimation(this.createResetAnimation(CharacterHuman.HARM_LEFT_LOW, this.harmLeftLowPosition, frame));
      animationParallel.addAnimation(this.createResetAnimation(CharacterHuman.HAND_LEFT, this.handLeftPosition, frame));
      animationParallel.addAnimation(this.createResetAnimation(CharacterHuman.LEG_RIGHT_HIGH, this.legRightHighPosition, frame));
      animationParallel.addAnimation(this.createResetAnimation(CharacterHuman.LEG_RIGHT_LOW, this.legRightLowPosition, frame));
      animationParallel.addAnimation(this.createResetAnimation(CharacterHuman.FOOT_RIGHT_ROTATION, this.footRotationRightPosition, frame));
      animationParallel.addAnimation(this.createResetAnimation(CharacterHuman.LEG_LEFT_HIGH, this.legLeftHighPosition, frame));
      animationParallel.addAnimation(this.createResetAnimation(CharacterHuman.LEG_LEFT_LOW, this.legLeftLowPosition, frame));
      animationParallel.addAnimation(this.createResetAnimation(CharacterHuman.FOOT_LEFT_ROTATION, this.footRotationLeftPosition, frame));
      return animationParallel;
   }

   /**
    * Run animation <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @return Run animation
    * @see jhelp.game3d.models.Character#obtainRunAnimation()
    */
   @Override
   public Animation obtainRunAnimation()
   {
      if(this.run == null)
      {
         this.run = new AnimationParallel();
         this.run.addAnimation(this.obtainRunLegsAnimation());

         // Right

         Node node = this.mainNode.getFirstNode(CharacterHuman.HARM_RIGHT_HIGH);
         PositionNode startPosition = new PositionNode(node);
         AnimationPositionNode animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX - 90,
               startPosition.angleY + 90, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_RUN / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 45, startPosition.angleY + 90, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_RUN, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX - 90,
               startPosition.angleY + 90, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.run.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.HARM_RIGHT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(1, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX, startPosition.angleY,
               startPosition.angleZ - 90, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.run.addAnimation(animationPositionNode);

         //

         // Left

         node = this.mainNode.getFirstNode(CharacterHuman.HARM_LEFT_HIGH);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX + 45,
               startPosition.angleY - 90, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_RUN / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 90, startPosition.angleY - 90, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_RUN, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX + 45,
               startPosition.angleY - 90, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.run.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.HARM_LEFT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(1, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX, startPosition.angleY,
               startPosition.angleZ + 90, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.run.addAnimation(animationPositionNode);
      }

      return this.run;
   }

   /**
    * Obtain a specific animation <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param animationIndex
    *           Animation ID
    * @return Specific animation
    * @see jhelp.game3d.models.Character#obtainSpecificAnimation(int)
    */
   @Override
   public Animation obtainSpecificAnimation(final int animationIndex)
   {
      switch(animationIndex)
      {
         case ANIMATION_00_WALK_SLOW_ONLY_LEGS:
            return this.obtainWalkSlowLegsAnimation();
         case ANIMATION_01_WALK_NORMAL_ONLY_LEGS:
            return this.obtainWalkNormalLegsAnimation();
         case ANIMATION_02_WALK_FAST_ONLY_LEGS:
            return this.obtainWalkFastLegsAnimation();
         case ANIMATION_03_RUN_ONLY_LEGS:
            return this.obtainRunLegsAnimation();
         case ANIMATION_04_EXPRESSION_YES:
            return this.obtainExpressionYes();
         case ANIMATION_05_EXPRESSION_NO:
            return this.obtainExpressionNo();
         case ANIMATION_06_EXPRESSION_I_DONT_KNOW:
            return this.obtainExpressionIDontKnow();
         case ANIMATION_07_EXPRESSION_YEAH:
            return this.obtainExpressionYeah();
         case ANIMATION_08_EXPRESSION_YAHOO:
            return this.obtainExpressionYahoo();
         case ANIMATION_09_EYES_LOOK_AT_RIGHT:
            return this.obtainEyesLookAtRight(CharacterHuman.TIME_EXPRESSION);
         case ANIMATION_10_EYES_LOOK_AT_LEFT:
            return this.obtainEyesLookAtLeft(CharacterHuman.TIME_EXPRESSION);
         case ANIMATION_11_EYES_LOOK_AT_UP:
            return this.obtainEyesLookAtUp(CharacterHuman.TIME_EXPRESSION);
         case ANIMATION_12_EYES_LOOK_AT_DOWN:
            return this.obtainEyesLookAtDown(CharacterHuman.TIME_EXPRESSION);
         case ANIMATION_13_EYES_LOOK_AT_NOISE:
            return this.obtainEyesLookAtNoise(CharacterHuman.TIME_EXPRESSION);
         case ANIMATION_14_EYES_LOOK_AT_DIFFERENT_DIRECTION:
            return this.obtainEyesLookAtDifferentDirection(CharacterHuman.TIME_EXPRESSION);
         case ANIMATION_15_EYES_DIZZY:
            return this.obtainEyesDizzy();
         case ANIMATION_16_EYES_START_POSITION:
            return this.obtainEyesLookAtStartPosition(CharacterHuman.TIME_EXPRESSION);
      }

      return null;
   }

   /**
    * Walk fast animation <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @return Walk fast animation
    * @see jhelp.game3d.models.Character#obtainWalkFastAnimation()
    */
   @Override
   public Animation obtainWalkFastAnimation()
   {
      if(this.walkFast == null)
      {
         this.walkFast = new AnimationParallel();
         this.walkFast.addAnimation(this.obtainWalkFastLegsAnimation());

         // Right

         Node node = this.mainNode.getFirstNode(CharacterHuman.HARM_RIGHT_HIGH);
         PositionNode startPosition = new PositionNode(node);
         AnimationPositionNode animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX - 45, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 45, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 45, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.walkFast.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.HARM_RIGHT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(1, startPosition);
         this.walkFast.addAnimation(animationPositionNode);

         //

         // Left

         node = this.mainNode.getFirstNode(CharacterHuman.HARM_LEFT_HIGH);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX + 45, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 45, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 45, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.walkFast.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.HARM_LEFT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(1, startPosition);
         this.walkFast.addAnimation(animationPositionNode);
      }

      return this.walkFast;
   }

   /**
    * Walk animation <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @return Walk animation
    * @see jhelp.game3d.models.Character#obtainWalkNormalAnimation()
    */
   @Override
   public Animation obtainWalkNormalAnimation()
   {
      if(this.walkNormal == null)
      {
         this.walkNormal = new AnimationParallel();
         this.walkNormal.addAnimation(this.obtainWalkNormalLegsAnimation());

         // Right

         Node node = this.mainNode.getFirstNode(CharacterHuman.HARM_RIGHT_HIGH);
         PositionNode startPosition = new PositionNode(node);
         AnimationPositionNode animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX - 25, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 25, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 25, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.walkNormal.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.HARM_RIGHT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(1, startPosition);
         this.walkNormal.addAnimation(animationPositionNode);

         //

         // Left

         node = this.mainNode.getFirstNode(CharacterHuman.HARM_LEFT_HIGH);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX + 25, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 25, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 25, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.walkNormal.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.HARM_LEFT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(1, startPosition);
         this.walkNormal.addAnimation(animationPositionNode);
      }

      return this.walkNormal;
   }

   /**
    * Walk slow animation <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @return Walk slow animation
    * @see jhelp.game3d.models.Character#obtainWalkSlowAnimation()
    */
   @Override
   public Animation obtainWalkSlowAnimation()
   {
      if(this.walkSlow == null)
      {
         this.walkSlow = new AnimationParallel();
         this.walkSlow.addAnimation(this.obtainWalkSlowLegsAnimation());

         // Right

         Node node = this.mainNode.getFirstNode(CharacterHuman.HARM_RIGHT_HIGH);
         PositionNode startPosition = new PositionNode(node);
         AnimationPositionNode animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX - 12, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 12, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 12, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.walkSlow.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.HARM_RIGHT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(1, startPosition);
         this.walkSlow.addAnimation(animationPositionNode);

         //

         // Left

         node = this.mainNode.getFirstNode(CharacterHuman.HARM_LEFT_HIGH);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(0, new PositionNode(startPosition.x, startPosition.y, startPosition.z, startPosition.angleX + 12, startPosition.angleY,
               startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK / 2, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX - 12, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         animationPositionNode.addFrame(CharacterHuman.TIME_WALK, new PositionNode(startPosition.x, startPosition.y, startPosition.z,
               startPosition.angleX + 12, startPosition.angleY, startPosition.angleZ, startPosition.scaleX, startPosition.scaleY, startPosition.scaleZ));
         this.walkSlow.addAnimation(animationPositionNode);

         node = this.mainNode.getFirstNode(CharacterHuman.HARM_LEFT_LOW);
         startPosition = new PositionNode(node);
         animationPositionNode = new AnimationPositionNode(node);
         animationPositionNode.addFrame(1, startPosition);
         this.walkSlow.addAnimation(animationPositionNode);
      }

      return this.walkSlow;
   }

   /**
    * Change body part color
    * 
    * @param bodyPart
    *           Body part to change
    * @param color
    *           New color to apply
    */
   public void setColor(final BodyPart bodyPart, final int color)
   {
      final String name = this.getName(bodyPart);

      if(name == null)
      {
         return;
      }

      final NodeWithMaterial nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(name);

      if(nodeWithMaterial == null)
      {
         return;
      }

      final Material material = nodeWithMaterial.getMaterial();

      switch(bodyPart)
      {
         case HAIR:
            this.setHairColor(color);
         break;
         default:
            material.setColorEmissive(Color4f.BLACK);
            material.setColorDiffuse(new Color4f(color));
            material.setTextureDiffuse(null);
         break;
      }
   }

   /**
    * Change dress scale factor
    * 
    * @param scale
    *           New dress scale factor
    */
   public void setDressScale(final float scale)
   {

      final Node dress = this.getCharacterNode().getFirstNode(CharacterHuman.DRESS);
      dress.setScale(dress.getScaleX(), scale, dress.getScaleZ());
   }

   /**
    * Change dress visibility
    * 
    * @param visible
    *           New dress visibility
    */
   public void setDressVisible(final boolean visible)
   {
      this.getCharacterNode().getFirstNode(CharacterHuman.DRESS).setVisible(visible);
   }

   /**
    * Change hair color
    * 
    * @param color
    *           New hair color
    */
   public void setHairColor(final int color)
   {
      final String textureName = "hair" + color;
      Texture texture = Texture.obtainTexture(textureName);

      if(texture == null)
      {
         final int size = 512;
         final int start = 6;
         final int end = size - 6;
         final int space = 32;
         final int number = (end - start) / space;
         final JHelpImage imageHair = new JHelpImage(size, size, color);
         imageHair.startDrawMode();
         final int colorLine = Color.brightness(color, 1.5);

         for(int i = 0, x = start; i < number; i++, x += space)
         {
            imageHair.drawVerticalLine(x, 0, size, colorLine);
         }

         imageHair.endDrawMode();
         texture = new Texture(textureName, imageHair);
      }

      this.setTexture(BodyPart.HAIR, texture);
   }

   /**
    * Change left eye texture
    * 
    * @param eyesTexture
    *           New left eye texture
    */
   public void setLeftEye(final EyesTexture eyesTexture)
   {
      this.setTextureForEye(CharacterHuman.EYE_LEFT, eyesTexture);
   }

   /**
    * Change right eye texture
    * 
    * @param eyesTexture
    *           New right eye texture
    */
   public void setRightEye(final EyesTexture eyesTexture)
   {
      this.setTextureForEye(CharacterHuman.EYE_RIGHT, eyesTexture);
   }

   /**
    * Change texture of body part
    * 
    * @param bodyPart
    *           Body part to change
    * @param texture
    *           New texture
    */
   public void setTexture(final BodyPart bodyPart, final Texture texture)
   {
      final String name = this.getName(bodyPart);

      if(name == null)
      {
         return;
      }

      final NodeWithMaterial nodeWithMaterial = (NodeWithMaterial) this.mainNode.getFirstNode(name);

      if(nodeWithMaterial == null)
      {
         return;
      }

      final Material material = nodeWithMaterial.getMaterial();
      material.originalSettings();
      material.setTextureDiffuse(texture);
   }
}