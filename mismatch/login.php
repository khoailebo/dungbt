<?php
$error = false;
$error_ms = "";
require_once('setsession.php');
if(!isset($_SESSION['user_id']))
{
    if(isset($_POST['submit']))
    {
        if(!empty($_POST['username']) && !empty($_POST['password']))
        {
            require_once('connectvars.php');
            $dbc = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_NAME);
            $username = mysqli_real_escape_string($dbc,trim($_POST['username']));
            $password = mysqli_real_escape_string($dbc,trim($_POST['password']));
            $query = "select user_id,username from mismatch_user where username = '$username' and password = SHA('$password')";
            $data = mysqli_query($dbc,$query);
            if(mysqli_num_rows($data) == 1)
            {
                $row = mysqli_fetch_array($data);
                $_SESSION['username'] = $row['username'];
                $_SESSION['user_id'] = $row['user_id'];
                setcookie('username',$row['username'],time() + 24 * 60 * 60 * 30);
                setcookie('user_id',$row['user_id'],time() + 24 * 60 * 60 * 30);
                mysqli_close($dbc);
                $http_url =  dirname($_SERVER['PHP_SELF']) . '/index.php';
                header('Location: ' . $http_url);
            }
            else {
                $error = true;
                $error_ms = "Please enter the correct username and password";
            }
        }
        else 
        {
            $error = true;
            $error_ms = "Wrong username or password";
        }
    }
}
?>

    <?php 
    $title_ms = 'Login';
    require_once('header.php');
        if($error == true)
        {
            echo '<p class="error">'.$error_ms.'</p>';
        }
        if(!isset($_SESSION['user_id']))
        {
    ?>
    <form action="<?php echo $_SERVER['PHP_SELF'] ?>" method="post">
    <fieldset>
        <legend>Login</legend>
        <label for="username">Username</label>
        <input type="text" name="username" value = "<?php if(!empty($username)) echo $username; ?>" id = "username"><br/>
        <label for="password">Password</label>
        <input type="password" name="password" id="password">
    </fieldset>
    <input type="submit" name="submit" value="Login">
</form>
    <?php
    }
    else 
    {
        echo '<p>You login successfully as '.$_SESSION['username'].'</p>';
    }
  require_once('footer.php');
  ?>
