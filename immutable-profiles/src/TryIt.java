import com.example.profiles.*;

public class TryIt {
    public static void main(String[] args) {
        ProfileService svc = new ProfileService();
        UserProfile p = svc.createMinimal("u1", "a@b.com");
        System.out.println("Email: " + p.getEmail());
        
        // UserProfile is now immutable, so this line would cause compilation error:
        // p.setEmail("evil@example.com");
        
        System.out.println("=> UserProfile is now immutable - no setters available.");
        
        // Demonstrate builder pattern with optional fields
        UserProfile fullProfile = new UserProfile.Builder("u2", "user@example.com")
            .displayName("John Doe")
            .phone("+1234567890")
            .address("123 Main St")
            .marketingOptIn(true)
            .twitter("@johndoe")
            .github("johndoe")
            .build();
            
        System.out.println("Full profile - Name: " + fullProfile.getDisplayName() + 
                          ", Marketing: " + fullProfile.isMarketingOptIn());
        
        // Demonstrate validation
        try {
            new UserProfile.Builder("", "invalid-email").build();
        } catch (IllegalArgumentException e) {
            System.out.println("Validation works: " + e.getMessage());
        }
        
        // Demonstrate display name trimming
        UserProfile longNameProfile = new UserProfile.Builder("u3", "test@example.com")
            .displayName("This is a very long display name that exceeds the 100 character limit and should be trimmed automatically")
            .build();
        System.out.println("Trimmed name length: " + longNameProfile.getDisplayName().length());
    }
}
