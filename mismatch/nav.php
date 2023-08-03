<?php
if(isset($_SESSION['user_id']))
{
    ?>
    <a href="index.php">Home</a>&#10084;
    <a href="viewprofile.php">View profile</a>&#10084;
    <a href = "editprofile.php">Edit profile</a>&#10084;
    <a href = "questionare.php">Questionnare</a>&#10084;
    <a href = "mymismatch.php">Your mismatcher</a>&#10084;
    <a href = "logout.php">Log out (<?php echo $_SESSION['username'] ?>)</a>&#10084;
    <?php
}
else 
{
    ?>
    <a href="login.php">Login</a>&#10084;
    <a href="signup.php">Sign up</a>&#10084;
    <?php
}
 ?>
 <hr/>