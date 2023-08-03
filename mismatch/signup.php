
    <?php 
    $title_ms = 'Sign up';
    require_once('header.php');
        if(isset($_POST['submit']))
        {
            $user_name = $_POST['username'];
            $password = $_POST['password'];
            $re_password = $_POST['re_password'];
            if(!empty($user_name) && !empty($password) && !empty($re_password))
            {
                require_once('connectvars.php');
                $dbc = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_NAME);
                $query = "Select * from mismatch_user where username = '$user_name'";
                $data = mysqli_query($dbc,$query);
                if(mysqli_num_rows($data) == 0)
                {
                    if($password == $re_password)
                    {
                        $query = "insert into mismatch_user(username,password,join_date)"
                        ."value('$user_name',SHA('$password'),NOW())";
                        mysqli_query($dbc,$query);
                        mysqli_close($dbc);
                        exit('<p>You sign up successfully</p><a href = "editprofile.php">Edit profile</a><br/><a href = "viewprofile.php">View Profile</a>');
                    }
                    else{
                        echo '<p class="error">You retype the wrong password!</p>';
                    }
                }
                else {
                    echo '<p class="error">Your username has beeen used!</p>';
                }
            }
            else echo '<p class="error">You must fill all information!</p>';
        }
    ?>
    <form action="<?php echo $_SERVER['PHP_SELF'] ?>" method="post">
    <fieldset>
        <legend>Sign up</legend>
        <label for="username">User name:</label>
        <input type="text" id="username" name="username"><br/>
        <label for="password">Password:</label>
        <input type="password" name="password" id = "password"><br/>
        <label for="re_password">Retype password:</label>
        <input type="password" name="re_password" id = "re_password">
    </fieldset>
        <input type="submit" name="submit" value="Sign up">
    </form>
<?php
  require_once('footer.php');
  ?>