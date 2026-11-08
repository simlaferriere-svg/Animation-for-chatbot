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
    
    private boolean animationFinished; // Set to true by your engine's animation callback/listener

    public CharacterAnimationController() {
        // Updated to match your exact asset filenames (including capital letters, spaces, and Talking.fbx)
        this.randomActions = Arrays.asList(
            "Waving.fbx", 
            "Looking.fbx", 
            "Shaking Hands 2.fbx",
            "Talking.fbx"
        );
        this.random = new Random();
        this.currentState = AnimationState.BREATHING;
        this.currentAnimationFile = "Breathing Idle.fbx";
        this.animationFinished = false;
    }

    /**
     * Call this method every frame in your game loop or update tick.
     */
    public void update() {
        // If the current animation is still playing, wait for it to finish
        if (!animationFinished) {
            return;
        }

        // Reset flag for the next cycle
        animationFinished = false;

        // Transition logic based on the finite state machine
        switch (currentState) {
            case BREATHING:
                // After breathing finishes, pick one of the random action animations
                currentAnimationFile = getRandomAction();
                currentState = AnimationState.RANDOM_ACTION;
                playAnimation(currentAnimationFile);
                break;

            case RANDOM_ACTION:
                // After a random action finishes, transition back to breathing idle
                currentAnimationFile = "Breathing Idle.fbx";
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

    /**
     * Stub method: Interface this with your 3D engine's animation player.
     */
    private void playAnimation(String fbxFilename) {
        System.out.println("[Animation Engine] Playing: " + fbxFilename);
        
        // TODO: Insert your engine-specific trigger here, e.g.:
        // animator.blend(fbxFilename, 0.2f); // 0.2s crossfade blend
    }

    /**
     * Call this from your animation library's completion listener/callback 
     * when an animation reaches its final frame.
     */
    public void onAnimationComplete() {
        this.animationFinished = true;
    }

    // Optional getters for debugging or UI integration
    public String getCurrentAnimationFile() {
        return currentAnimationFile;
    }

    public AnimationState getCurrentState() {
        return currentState;
    }
}