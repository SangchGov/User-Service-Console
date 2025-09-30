import org.example.Entity.DTO.UserDTO;
import org.example.Entity.User;
import org.example.Service.UserService;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private static UserService userService;

    @BeforeAll
    static void setup() {
        userService = new UserService();
    }

    @Test
    void testCreateUser() {
        UserDTO dto = new UserDTO("Aleks", "aleks.com", 18);
        User created = userService.createUser(dto);

        assertNotNull(created.getId(), "User must have an ID");
        assertEquals("Aleks", created.getName());
        assertEquals("aleks.com", created.getEmail());
        assertEquals(18, created.getAge());

        userService.deleteUser(created.getId());
    }

    @Test
    void testGetUserById() {
        UserDTO dto = new UserDTO("Aleks", "aleks.com", 18);
        User created = userService.createUser(dto);

        Optional<User> fetched = userService.getUserById(created.getId());
        assertTrue(fetched.isPresent());
        User user = fetched.get();
        assertEquals("Aleks", user.getName());
        assertEquals("aleks.com", user.getEmail());
        assertEquals(18, user.getAge());

        userService.deleteUser(created.getId());
    }

    @Test
    void testUpdateUser() {
        UserDTO dto = new UserDTO("Aleks", "aleks.com", 18);
        User created = userService.createUser(dto);

        UserDTO updateDto = new UserDTO("Aleks Updated", "aleks.updated@example.com", 19);
        Optional<User> updatedOpt = userService.updateUser(created.getId(), updateDto);
        assertTrue(updatedOpt.isPresent());
        User updated = updatedOpt.get();
        assertEquals("Aleks Updated", updated.getName());
        assertEquals("aleks.updated@example.com", updated.getEmail());
        assertEquals(19, updated.getAge());

        userService.deleteUser(created.getId());
    }

    @Test
    void testGetAllUsers() {
        UserDTO dto = new UserDTO("Aleks", "aleks.com", 18);
        User created = userService.createUser(dto);

        List<User> users = userService.getAllUsers();
        assertTrue(users.stream().anyMatch(u -> u.getId().equals(created.getId())));

        userService.deleteUser(created.getId());
    }

    @Test
    void testDeleteUser() {
        UserDTO dto = new UserDTO("Aleks", "aleks.com", 18);
        User created = userService.createUser(dto);

        boolean deleted = userService.deleteUser(created.getId());
        assertTrue(deleted);

        Optional<User> fetched = userService.getUserById(created.getId());
        assertFalse(fetched.isPresent());
    }
}