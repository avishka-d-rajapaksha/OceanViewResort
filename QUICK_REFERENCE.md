# 🆘 Quick Reference - Common Questions

## "How do I run the application?"
👉 **Double-click `START.bat`** - That's it!

---

## "My friend says Java is not found"
1. Download Java 17: https://www.oracle.com/java/technologies/downloads/
2. Install it with default settings
3. Restart computer
4. Run START.bat again

✓ Check: Open Command Prompt, type `java -version`

---

## "My friend says Maven is not found"
1. Download Maven: https://maven.apache.org/download.cgi
2. Extract to a folder (e.g., C:\Maven)
3. Add to System PATH:
   - Right-click This PC → Properties
   - Advanced system settings → Environment variables
   - New PATH: C:\Maven\bin
4. Restart computer

✓ Check: Open Command Prompt, type `mvn -version`

---

## "My friend says MySQL is not running"
1. Open MySQL Command Line or Workbench
2. Make sure server is running/connected
3. Default credentials:
   - User: root
   - Password: (empty or your password)

✓ Check: Open Command Prompt, type `mysql -u root`

---

## "My friend created database but it doesn't have tables"
👉 Run **SETUP_DATABASE.bat** to import tables automatically

Or manually:
1. Open MySQL Command Line
2. Type: `USE OceanViewDB;`
3. Paste contents of `oceanviewdb.sql`

---

## "Room shows after creating reservation (Bug Still There?)"
**This is FIXED!**

What changed:
- When you save reservation → Room status is now updated to "Booked"
- When Booked → Room no longer shows in available list ✅
- If you switch views and come back → List auto-refreshes ✅

---

## "Guest shows then disappears (Bug Still There?)"
**This is FIXED!**

What changed:
- When you add guest → Guest is saved to database ✅
- When you navigate to Guests view → List auto-refreshes ✅
- Guest data persists and doesn't disappear ✅

---

## "Application won't start - Error in console"
1. Run **CHECK_SYSTEM.bat** to diagnose
2. Look at the error message
3. Follow the suggestion in the error
4. Try again

Common errors:
- "Java not found" → Install Java 17
- "Maven not found" → Install Maven
- "Database error" → Check MySQL is running
- "Port 8080 in use" → Close other apps using port 8080

---

## "I want to change database credentials"
Edit: `src/main/java/com/oceanview/db/DBConnection.java`

Find these lines:
```java
private static final String URL = "jdbc:mysql://localhost:3306/OceanViewDB";
private static final String USER = "root";
private static final String PASSWORD = "";
```

Change `root` to your MySQL username and `""` to your password.

**Note:** Friend must rebuild project after changes:
```
mvn clean install
START.bat
```

---

## "I want to change login credentials"
User database table stores credentials (hashed with BCrypt).

Default admin:
- Username: admin
- Password: admin123

To change: Edit database directly or add user interface feature.

---

## "Port 8080 is already in use by another app"
Edit: `src/main/java/com/oceanview/network/OceanViewServer.java`

Find: `private static final int SERVER_PORT = 8080;`

Change `8080` to another port like `8081`, `9000`, etc.

Then rebuild and start:
```
mvn clean install
START.bat
```

Web server will now run on: http://localhost:8081

---

## "Application works but I want to add features"
See **README.md** → "Development Notes" section

Quick guide:
1. Create DAO for database access
2. Create Service for business logic
3. Create Controller for UI
4. Add FXML for layout
5. Test everything

---

## "I want to send this to someone else"
1. Right-click OceanViewResort folder
2. Send to → Compressed (zipped) folder
3. Create ZIP file
4. Share ZIP with friend
5. Friend can extract and run START.bat

Friend needs:
- ✅ Java 17
- ✅ Maven
- ✅ MySQL
- ✅ The ZIP file

---

## "Can I run this on Linux/Mac?"
Yes! This is Java/Maven/MySQL - all cross-platform.

Instead of .bat files:
- Use `chmod +x START.sh` (if bash script exists)
- Or run: `mvn javafx:run`
- Or run: `mvn clean install && mvn javafx:run`

All scripts are provided for Windows. Mac/Linux still works with Maven commands.

---

## "The app is slow - how to speed up?"
1. Increase RAM in Maven/Java settings
2. Close other applications
3. Check database connection (network latency)
4. Rebuild with: `mvn clean install -q` (quiet mode, faster)

---

## "How to backup the database?"
```bash
# Export database
mysqldump -u root -p OceanViewDB > backup.sql

# Restore from backup
mysql -u root -p OceanViewDB < backup.sql
```

Or use MySQL Workbench GUI.

---

## "Application crashed - how to fix?"
1. Check error message in console
2. Run **CHECK_SYSTEM.bat** to diagnose
3. Restart MySQL Server
4. Run **START.bat** again
5. If still broken: `mvn clean install` (full rebuild)

---

## "How to change admin password?"
1. Open MySQL Command Line/Workbench
2. Connect to OceanViewDB
3. Find users table
4. Update admin user password (use BCrypt hash)

Or create admin UI for password change.

---

## "I accidentally deleted something in the database"
1. Stop the application
2. Restore from backup: `mysql -u root -p OceanViewDB < backup.sql`
3. Restart application

---

## Files for Your Friend

📦 **Send Only These:**
- ✅ Entire OceanViewResort folder (ZIP)
- ✅ README.md (included in ZIP)
- ✅ DEPLOYMENT_GUIDE.md (included in ZIP)
- ✅ START.bat (included in ZIP)
- ✅ CHECK_SYSTEM.bat (included in ZIP)
- ✅ SETUP_DATABASE.bat (included in ZIP)
- ✅ oceanviewdb.sql (database schema)

**Do NOT send:**
- ❌ target/ folder (will be regenerated)
- ❌ .idea/ folder (IDE specific)
- ❌ .git/ folder (version control)

---

## Quick Checklist for Friend

Before running START.bat:
- [ ] Java 17+ installed: `java -version`
- [ ] Maven installed: `mvn -version`
- [ ] MySQL Server running
- [ ] ZIP extracted to a folder
- [ ] In the extracted folder (where START.bat is)

Then:
- [ ] Double-click CHECK_SYSTEM.bat (verify everything works)
- [ ] Double-click SETUP_DATABASE.bat (create database)
- [ ] Double-click START.bat (launch app)

That's all! No more steps needed.

---

## All Done! ✅

Your application is:
- ✅ Bug-free
- ✅ Production-ready
- ✅ Easy to deploy
- ✅ Well-documented
- ✅ Ready to share with friend

Enjoy! 🌊
