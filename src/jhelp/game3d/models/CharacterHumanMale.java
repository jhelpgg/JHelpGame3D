package jhelp.game3d.models;

import jhelp.engine.Node;
import jhelp.engine.NodeWithMaterial.TwoSidedState;
import jhelp.engine.ObjectClone;
import jhelp.engine.Point2D;
import jhelp.engine.VirtualBox;
import jhelp.engine.geom.Box;
import jhelp.engine.geom.Revolution;
import jhelp.engine.geom.Sphere;
import jhelp.engine.util.Tool3D;
import jhelp.game3d.resources.EyesTexture;

/**
 * Human male character
 * 
 * @author JHelp
 */
public class CharacterHumanMale
      extends CharacterHuman
{
   /** All body main node */
   private static Node allCharacter;

   /**
    * Obtain/create the all body node
    * 
    * @return All body node
    */
   private static Node obtainCharacter()
   {
      if(CharacterHumanMale.allCharacter != null)
      {
         return Tool3D.createCloneHierarchy(CharacterHumanMale.allCharacter);
      }

      CharacterHumanMale.allCharacter = new Node();

      // Load the head
      final VirtualBox box = CharacterHuman.addHumanHeadTo(CharacterHumanMale.allCharacter);

      // Create body
      final Revolution body = new Revolution(360f, 1, 30, 1f);
      body.nodeName = CharacterHuman.BODY;
      final float neckWidth = box.getMaxX() / 2f;
      final float maxWidth = box.getMaxX();
      final float hipWidth = (maxWidth + neckWidth) / 2f;
      final float height = (box.getMinY() - box.getMaxY()) * 1.5f;
      body.appendLine(new Point2D(neckWidth, 0), new Point2D(neckWidth, height * 0.25f));
      body.appendLine(new Point2D(neckWidth, height * 0.25f), new Point2D(maxWidth, height * 0.40f));
      body.appendLine(new Point2D(maxWidth, height * 0.40f), new Point2D(maxWidth, height));
      body.appendLine(new Point2D(maxWidth, height), new Point2D(neckWidth * 0.1f, height * 1.15f));
      body.appendLine(new Point2D(neckWidth * 0.1f, height * 1.15f), new Point2D(0, height * 1.15f));
      body.refreshRevolution(0, 1);
      body.setTwoSidedState(TwoSidedState.FORCE_TWO_SIDE);
      body.setPosition(-4.917383E-7f, -2.8377173f, -2.2274594f);
      CharacterHumanMale.allCharacter.addChild(body);

      // Dress
      final Revolution dress = new Revolution(360f, 1, 30, 1f);
      dress.setVisible(false);
      dress.nodeName = CharacterHuman.DRESS;
      dress.appendLine(new Point2D(maxWidth, 0), new Point2D(maxWidth * 2f, height * 0.9f));
      dress.refreshRevolution(0, 1);
      dress.setPosition(0, height, 0);
      dress.setTwoSidedState(TwoSidedState.FORCE_TWO_SIDE);
      body.addChild(dress);

      // Right harm high
      final Revolution rightHarmHigh = new Revolution(360f, 16, 30, 1f);
      rightHarmHigh.nodeName = CharacterHuman.HARM_RIGHT_HIGH;
      rightHarmHigh.appendQuad(new Point2D(0, neckWidth / 4), new Point2D(neckWidth / 4, neckWidth / 4), new Point2D(neckWidth / 2, 0));
      rightHarmHigh.appendLine(new Point2D(neckWidth / 2, 0), new Point2D(neckWidth / 2, height * 0.32f));
      rightHarmHigh.appendQuad(new Point2D(neckWidth / 2, height * 0.32f), new Point2D(neckWidth / 4, (height * 0.32f) - (neckWidth / 4)), new Point2D(0,
            (height * 0.32f) - (neckWidth / 4)));
      rightHarmHigh.setPosition(-maxWidth * 1.25f, height * 0.45f, 0);
      rightHarmHigh.refreshRevolution(0, 1);
      rightHarmHigh.setTwoSidedState(TwoSidedState.FORCE_TWO_SIDE);
      rightHarmHigh.limitAngleY(0, 180);
      rightHarmHigh.limitAngleZ(-90, 0);
      body.addChild(rightHarmHigh);

      // Right harm low
      final ObjectClone harmRightLow = new ObjectClone(rightHarmHigh);
      harmRightLow.nodeName = CharacterHuman.HARM_RIGHT_LOW;
      harmRightLow.setPosition(0, height * 0.32f, 0);
      harmRightLow.setScale(1, 0.9f, 1);
      harmRightLow.limitAngleX(0, 0);
      harmRightLow.limitAngleY(0, 0);
      harmRightLow.limitAngleZ(-152, 0);
      rightHarmHigh.addChild(harmRightLow);

      // Right hand
      final Sphere handRight = new Sphere();
      handRight.nodeName = CharacterHuman.HAND_RIGHT;
      handRight.setScale(neckWidth / 2, neckWidth * 0.75f, neckWidth / 2);
      handRight.setPosition(0, height * 0.32f * 1.1f, 0);
      harmRightLow.addChild(handRight);

      // Left harm high
      final ObjectClone leftHarmHigh = new ObjectClone(rightHarmHigh);
      leftHarmHigh.nodeName = CharacterHuman.HARM_LEFT_HIGH;
      leftHarmHigh.setPosition(maxWidth * 1.25f, height * 0.45f, 0);
      leftHarmHigh.setTwoSidedState(TwoSidedState.FORCE_TWO_SIDE);
      leftHarmHigh.limitAngleY(-180, 0);
      leftHarmHigh.limitAngleZ(0, 90);
      body.addChild(leftHarmHigh);

      // Left harm low
      final ObjectClone leftHarmLow = new ObjectClone(rightHarmHigh);
      leftHarmLow.nodeName = CharacterHuman.HARM_LEFT_LOW;
      leftHarmLow.setPosition(0, height * 0.32f, 0);
      leftHarmLow.setScale(1, 0.9f, 1);
      leftHarmLow.limitAngleX(0, 0);
      leftHarmLow.limitAngleY(0, 0);
      leftHarmLow.limitAngleZ(0, 152);
      leftHarmHigh.addChild(leftHarmLow);

      // Left hand
      final ObjectClone handLeft = new ObjectClone(handRight);
      handLeft.nodeName = CharacterHuman.HAND_LEFT;
      handLeft.setScale(neckWidth / 2, neckWidth * 0.75f, neckWidth / 2);
      handLeft.setPosition(0, height * 0.32f * 1.1f, 0);
      leftHarmLow.addChild(handLeft);

      // Right leg high
      final ObjectClone rightLegHigh = new ObjectClone(rightHarmHigh);
      rightLegHigh.nodeName = CharacterHuman.LEG_RIGHT_HIGH;
      rightLegHigh.setScale(1.1f, 1.5f, 1.1f);
      rightLegHigh.setPosition(-hipWidth * 0.7f, height * 1.05f, 0);
      rightLegHigh.limitAngleX(-130, 90);
      rightLegHigh.limitAngleY(0, 0);
      rightLegHigh.limitAngleZ(-90, 45);
      body.addChild(rightLegHigh);

      // Right leg low
      final ObjectClone rightLegLow = new ObjectClone(rightHarmHigh);
      rightLegLow.nodeName = CharacterHuman.LEG_RIGHT_LOW;
      rightLegLow.setScale(1f, 0.9f, 1f);
      rightLegLow.setPosition(0, height * 0.32f, 0);
      rightLegLow.limitAngleX(0, 152);
      rightLegLow.limitAngleY(0, 0);
      rightLegLow.limitAngleZ(0, 0);
      rightLegHigh.addChild(rightLegLow);

      // Right foot rotation
      final Node rightFootRotation = new Node();
      rightFootRotation.nodeName = CharacterHuman.FOOT_RIGHT_ROTATION;
      rightFootRotation.setPosition(0, height * 0.32f * 1.1f, 0);
      rightFootRotation.limitAngleX(0, 45);
      rightLegLow.addChild(rightFootRotation);

      // Right foot
      final Box rightFoot = new Box();
      rightFoot.nodeName = CharacterHuman.FOOT_RIGHT;
      rightFoot.setPosition(0, 0, neckWidth * 0.5f);
      rightFoot.setScale(neckWidth, neckWidth / 2, neckWidth * 2);
      rightFootRotation.addChild(rightFoot);

      // Left leg high
      final ObjectClone leftLegHigh = new ObjectClone(rightHarmHigh);
      leftLegHigh.nodeName = CharacterHuman.LEG_LEFT_HIGH;
      leftLegHigh.setScale(1.1f, 1.5f, 1.1f);
      leftLegHigh.setPosition(hipWidth * 0.7f, height * 1.05f, 0);
      leftLegHigh.limitAngleX(-130, 90);
      leftLegHigh.limitAngleY(0, 0);
      leftLegHigh.limitAngleZ(-45, 90);
      body.addChild(leftLegHigh);

      // Left leg low
      final ObjectClone leftLegLow = new ObjectClone(rightHarmHigh);
      leftLegLow.nodeName = CharacterHuman.LEG_LEFT_LOW;
      leftLegLow.setScale(1f, 0.9f, 1f);
      leftLegLow.setPosition(0, height * 0.32f, 0);
      leftLegLow.limitAngleX(0, 152);
      leftLegLow.limitAngleY(0, 0);
      leftLegLow.limitAngleZ(0, 0);
      leftLegHigh.addChild(leftLegLow);

      // Left foot rotation
      final Node leftFootRotation = new Node();
      leftFootRotation.nodeName = CharacterHuman.FOOT_LEFT_ROTATION;
      leftFootRotation.setPosition(0, height * 0.32f * 1.1f, 0);
      leftFootRotation.limitAngleX(0, 45);
      leftLegLow.addChild(leftFootRotation);

      // Left foot
      final ObjectClone leftFoot = new ObjectClone(rightFoot);
      leftFoot.nodeName = CharacterHuman.FOOT_LEFT;
      leftFoot.setPosition(0, 0, neckWidth * 0.5f);
      leftFoot.setScale(neckWidth, neckWidth / 2, neckWidth * 2);
      leftFootRotation.addChild(leftFoot);

      // END
      CharacterHumanMale.allCharacter.centerGravityPoint();
      return CharacterHumanMale.allCharacter;
   }

   /**
    * Create a new instance of CharacterHumanMale
    */
   public CharacterHumanMale()
   {
      super(CharacterHumanMale.obtainCharacter());
      this.setRightEye(EyesTexture.EYE_BROWN);
      this.setLeftEye(EyesTexture.EYE_BROWN);
   }
}