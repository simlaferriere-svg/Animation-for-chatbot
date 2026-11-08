
import java.util.Random;
import java.util.List;
import java.util.Arrays;

public class CharacterAnimationController {

    public enum AnimationState {
        BREATHING,
        RANDOM_ACTION,
        IDLE
    }

    private AnimationState currentState;
    private String currentAnimationFile;
    private final List<String> randomActions;
    private final Random random;
    
    private boolean animationFinished;

    public CharacterAnimationController() {
        this.randomActions = Arrays.asList(
            "waving.fbx", 
            "looking.fbx", 
            "shakinghands2.fbx"
        );
        this.random = new Random();
        this.currentState = AnimationState.BREATHING;
        this.currentAnimationFile = "breathingidl.fbx";
        this.animationFinished = false;
    }

    public void update() {
        if (!animationFinished) {
            return;
        }

        animationFinished = false;

        switch (currentState) {
            case BREATHING:
                currentAnimationFile = getRandomAction();
                currentState = AnimationState.RANDOM_ACTION;
                playAnimation(currentAnimationFile);
                break;

            case RANDOM_ACTION:
                currentAnimationFile = "breathingidl.fbx";
                currentState = AnimationState.BREATHING;
                playAnimation(currentAnimationFile);
                break;
                
            default:
                break;
        }
    }

    private String getRandomAction() {
        int index = random.nextInt(randomActions.size());
        return randomActions.get(index);
    }

    private void playAnimation(String fbxFilename) {
        System.out.println("[Animation Engine] Playing: " + fbxFilename);
    }

    public void onAnimationComplete() {
        this.animationFinished = true;
    }

    public String getCurrentAnimationFile() {
        return currentAnimationFile;
    }

    public AnimationState getCurrentState() {
        return currentState;
    }
}